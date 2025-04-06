package com.cungthinh.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cungthinh.productservice.dto.request.ProductCreationRequestV2;
import com.cungthinh.productservice.dto.response.ApiResponse;
import com.cungthinh.productservice.dto.response.ProductResponseV2;
import com.cungthinh.productservice.service.ProductService;

@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2 {
    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody ProductCreationRequestV2 request) {
        try {
            String response = productService.createProductV2(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-spu-info/{productId}")
    public ApiResponse<Object> getSpuInfo(@PathVariable("productId") String productId) {
        ProductResponseV2 result = productService.getSpuInfo(productId);
        return ApiResponse.success(result, "Get SPU info successfully");
    }
}
