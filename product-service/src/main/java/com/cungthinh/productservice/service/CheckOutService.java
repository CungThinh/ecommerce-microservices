package com.cungthinh.productservice.service;

import com.cungthinh.productservice.dto.request.ValidateCartAndProductRequest;
import com.cungthinh.productservice.dto.response.CartProductResponse;
import com.cungthinh.productservice.dto.response.ValidateCartAndProductResponse;
import com.cungthinh.productservice.entity.core.Cart;
import com.cungthinh.productservice.entity.core.Product;
import com.cungthinh.productservice.exceptions.CustomException;
import com.cungthinh.productservice.exceptions.ErrorCode;
import com.cungthinh.productservice.repository.CartRepository;
import com.cungthinh.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CheckOutService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final InventoryService inventoryService;

    public CheckOutService(ProductRepository productRepository, CartRepository cartRepository, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.inventoryService = inventoryService;
    }

    public ValidateCartAndProductResponse validateCartAndProduct(ValidateCartAndProductRequest request) {
        log.info("Request {}", request);
        Cart userCart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        List<CartProductResponse> cartProductResponses = new ArrayList<>();

        request.getCartProducts().forEach(cartProduct -> {
            Product product = productRepository.findById(cartProduct.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
            if (!inventoryService.isStockAvailable(product.getId(), cartProduct.getQuantity())) {
                throw new CustomException(ErrorCode.INVENTORY_OUT_OF_STOCK);
            }

            cartProductResponses.add(CartProductResponse.builder()
                    .productId(product.getId())
                    .quantity(cartProduct.getQuantity())
                    .productName(product.getName())
                    .price(product.getPrice())
                    .build());
        });

        return ValidateCartAndProductResponse.builder()
                .cartId(userCart.getId())
                .cartProducts(cartProductResponses).build();
    }
}
