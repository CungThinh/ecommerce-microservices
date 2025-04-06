package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cungthinh.productservice.entity.core.SpuProduct;

@Repository
public interface SpuProductRepository extends MongoRepository<SpuProduct, String> {}
