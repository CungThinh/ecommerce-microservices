package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cungthinh.productservice.entity.core.CartProduct;

public interface CartProductRepository extends MongoRepository<CartProduct, String> {}
