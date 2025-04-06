package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cungthinh.productservice.entity.core.SkuProduct;

@Repository
public interface SkuProductRepository extends MongoRepository<SkuProduct, String> {}
