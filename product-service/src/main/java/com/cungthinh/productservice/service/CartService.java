package com.cungthinh.productservice.service;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cungthinh.productservice.dto.request.CartCreationRequest;
import com.cungthinh.productservice.dto.request.CartUpdateRequest;
import com.cungthinh.productservice.dto.response.CartResponse;
import com.cungthinh.productservice.entity.core.Cart;
import com.cungthinh.productservice.entity.core.CartProduct;
import com.cungthinh.productservice.entity.core.Product;
import com.cungthinh.productservice.mapper.CartMapper;
import com.cungthinh.productservice.repository.CartProductRepository;
import com.cungthinh.productservice.repository.CartRepository;
import com.cungthinh.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final MongoTemplate mongoTemplate;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CartProductRepository cartProductRepository;

    public CartService(
            CartRepository cartRepository,
            MongoTemplate mongoTemplate,
            ProductRepository productRepository,
            CartMapper cartMapper,
            CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.mongoTemplate = mongoTemplate;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
        this.cartProductRepository = cartProductRepository;
    }

    public void createUserCart(String userId, Product product, int quantity) {
        Cart cart = Cart.builder().userId(userId).build();
        CartProduct cartProduct = CartProduct.builder()
                .productId(product.getId())
                .quantity(quantity)
                .price(product.getPrice())
                .productName(product.getName())
                .build();

        cart.getCartProducts().add(cartProduct);
        cart.setCartProductCount(cart.getCartProducts().size());
        cartProductRepository.save(cartProduct);
        cartRepository.save(cart);
    }

    public void updateCartProductQuantity(String userId, String productId, int quantity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId")
                .is(userId)
                .and("cartProducts.productId")
                .is(productId));
        Update update = new Update();
        update.inc("cartProducts.$.quantity", quantity);

        mongoTemplate.updateFirst(query, update, Cart.class);
    }

    public void deleteCartProduct(String userId, String productId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId")
                .is(userId)
                .and("cartProducts.productId")
                .is(productId)
                .and("cartProductCount")
                .gt(0));
        Update update = new Update();
        update.pull("cartProducts", Query.query(Criteria.where("productId").is(productId)));
        update.inc("cartProductCount", -1);

        mongoTemplate.updateFirst(query, update, Cart.class);
    }

    public void addNewCartProduct(Cart userCart, Product product, int quantity) {
        CartProduct cartProduct = CartProduct.builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
        userCart.getCartProducts().add(cartProduct);
        userCart.setCartProductCount(userCart.getCartProductCount() + 1);
        cartProductRepository.save(cartProduct);
        cartRepository.save(userCart);
    }

    public void addToCart(CartCreationRequest request) {
        Product foundProduct =
                productRepository.findById(request.getProductId()).orElseThrow();
        Cart userCart = cartRepository.findByUserId(request.getUserId());

        if (userCart == null) {
            createUserCart(request.getUserId(), foundProduct, request.getQuantity());
            return;
        }

        if (userCart.getCartProducts() == null) {
            userCart.setCartProducts(new ArrayList<>());
        }

        boolean productExistsInCart = userCart.getCartProducts().stream()
                .anyMatch(cartProduct -> cartProduct.getProductId().equals(request.getProductId()));

        if (!productExistsInCart) {
            addNewCartProduct(userCart, foundProduct, request.getQuantity());
        } else {
            updateCartProductQuantity(request.getUserId(), request.getProductId(), request.getQuantity());
        }
    }

    public void updateCart(CartUpdateRequest request) {
        productRepository.findById(request.getProductId()).orElseThrow();
        if (request.getQuantity() == 0) {
            deleteCartProduct(request.getUserId(), request.getProductId());
        }
        updateCartProductQuantity(
                request.getUserId(), request.getProductId(), request.getQuantity() - request.getOldQuantity());
    }

    public CartResponse getCartByUserId(String userId) {
        Cart userCart = cartRepository.findByUserId(userId);
        return cartMapper.toCartResponse(userCart);
    }

    public void clearCart(String cartId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(cartId));
        Update update = new Update();
        update.set("cartProducts", new ArrayList<>());
        update.set("cartProductCount", 0);

        mongoTemplate.updateFirst(query, update, Cart.class);
    }
}
