package com.cungthinh.productservice.controller;

import com.cungthinh.productservice.dto.request.CartCreationRequest;
import com.cungthinh.productservice.dto.request.CartUpdateRequest;
import com.cungthinh.productservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("")
    public ResponseEntity<?> createCart(@RequestBody CartCreationRequest request) {
        cartService.addToCart(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody CartUpdateRequest request) {
        cartService.updateCart(request);
        return ResponseEntity.ok().build();
    }
}
