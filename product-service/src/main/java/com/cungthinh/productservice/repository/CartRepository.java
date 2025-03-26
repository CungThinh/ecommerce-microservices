package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cungthinh.productservice.entity.core.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(String userId);
}
