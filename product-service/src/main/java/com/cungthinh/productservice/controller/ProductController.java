package com.cungthinh.productservice.controller;

import com.cungthinh.productservice.dto.request.ProductCreationRequest;
import com.cungthinh.productservice.dto.request.ReserveProductRequest;
import com.cungthinh.productservice.dto.response.ApiResponse;
import com.cungthinh.productservice.dto.response.ProductCreationResponse;
import com.cungthinh.productservice.dto.response.ReserveProductResponse;
import com.cungthinh.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreationRequest request) {
        try {
            ProductCreationResponse response = productService.createProduct(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getProductBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.getProductBySlug(slug));
    }

    @GetMapping("/drafts/all")
    public ResponseEntity<?> getAllDrafts() {
        return ResponseEntity.ok(productService.getAllDraftProducts());
    }

    @PostMapping("/publish/{productId}")
    public ResponseEntity<?> publishProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.publishOrUnPublishProduct(productId, true));
    }

    @PostMapping("/unpublish/{productId}")
    public ResponseEntity<?> unPublishProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.publishOrUnPublishProduct(productId, false));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchProduct(@PathVariable String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

    @PostMapping("/reserve")
    public ApiResponse<Object> reserveProduct(@RequestBody ReserveProductRequest request) {
        ReserveProductResponse response = productService.reserveProduct(request);
        return ApiResponse.success(response, "Reserve products successfully");
    }
}
