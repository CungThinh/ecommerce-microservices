package com.cungthinh.productservice.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cungthinh.productservice.dto.request.ValidateCartAndProductRequest;
import com.cungthinh.productservice.dto.response.ApiResponse;
import com.cungthinh.productservice.dto.response.ValidateCartAndProductResponse;
import com.cungthinh.productservice.service.CheckOutService;

@RestController
@RequestMapping("/api/v1/validation/checkout")
public class CheckoutValidationController {
    @Autowired
    private CheckOutService checkOutService;

    @PostMapping("")
    public ResponseEntity<Object> validateCheckOut(@Valid @RequestBody ValidateCartAndProductRequest request) {
        ValidateCartAndProductResponse result = checkOutService.validateCartAndProduct(request);
        ApiResponse<Object> response = ApiResponse.success(result, "Validate successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
