package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cungthinh.productservice.entity.core.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(String userId);
}
