package com.cungthinh.productservice.repository;

import com.cungthinh.productservice.entity.core.CartProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartProductRepository extends MongoRepository<CartProduct, String> {
}
