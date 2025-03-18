package com.cungthinh.productservice.repository;

import com.cungthinh.productservice.entity.core.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findByUserId(String userId);
}
