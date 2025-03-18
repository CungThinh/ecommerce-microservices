package com.cungthinh.productservice.service;
import com.mongodb.client.result.UpdateResult;
import com.cungthinh.productservice.dto.request.CartProduct;
import com.cungthinh.productservice.dto.request.ProductCreationRequest;
import com.cungthinh.productservice.dto.request.ReserveProductRequest;
import com.cungthinh.productservice.dto.response.ProductCreationResponse;
import com.cungthinh.productservice.dto.response.ProductResponse;
import com.cungthinh.productservice.dto.response.ReserveProductResponse;
import com.cungthinh.productservice.entity.core.Product;
import com.cungthinh.productservice.entity.specification.Specification;
import com.cungthinh.productservice.mapper.ProductMapper;
import com.cungthinh.productservice.repository.ProductRepository;
import com.cungthinh.productservice.service.specification.SpecificationService;
import com.cungthinh.productservice.utils.SlugGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SpecificationService specificationService;
    private final ProductLockService productLockService;
    private final ProductMapper productMapper;
    private final MongoTemplate mongoTemplate;
    private final InventoryService inventoryService;

    public ProductService(ProductRepository productRepository, SpecificationService specificationService, ProductLockService productLockService, ProductMapper productMapper, MongoTemplate mongoTemplate, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.specificationService = specificationService;
        this.productLockService = productLockService;
        this.productMapper = productMapper;
        this.mongoTemplate = mongoTemplate;
        this.inventoryService = inventoryService;
    }

    public ProductCreationResponse createProduct(ProductCreationRequest request) {

        Specification specs = specificationService.createSpecification(request.getCategory(), request.getSpecs());

        Product newProduct = Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .specs(specs)
                .imageUri(request.getImageUri())
                .quantity(request.getQuantity())
                .build();

        String productSlug = SlugGenerator.toSlug(request.getName());
        newProduct.setSlug(productSlug);

        productRepository.save(newProduct);
        inventoryService.createProductInventory(newProduct);
        return productMapper.toProductCreationResponse(newProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllProductsWithSelectedFields();
    }

    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow();
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllDraftProducts() {
        Query query = new Query();
        query.addCriteria(Criteria.where("isDraft").is(true));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return productMapper.toProductResponseList(products);
    }

    public int publishOrUnPublishProduct(String id, boolean publish) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        if(publish) {
            update.set("isPublished", true);
            update.set("isDraft", false);
        } else {
            update.set("isPublished", false);
            update.set("isDraft", true);
        }
        UpdateResult result = mongoTemplate.updateFirst(query, update, Product.class);
        log.info("Count {}", result.getModifiedCount());
        return (int) result.getModifiedCount();
    }

    public List<ProductResponse> searchProduct(String keyword) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(keyword);
        Query query = TextQuery.queryText(textCriteria).sortByScore()
                .addCriteria(Criteria.where("isPublished").is(true));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return productMapper.toProductResponseList(products);
    }

    public ReserveProductResponse reserveProduct(ReserveProductRequest request) {
        ReserveProductResponse response = new ReserveProductResponse();
        for(CartProduct product: request.getCartProducts()) {
            String keyLock = productLockService.acquireLock(product.getProductId(), request.getCartId(), product.getQuantity());
            log.info("Key lock {}", keyLock);
            response.addProductStatus(product.getProductId(), keyLock != null);
            if(keyLock != null) {
                productLockService.releaseLock(keyLock);
            }
        }
        return response;
    }
}
