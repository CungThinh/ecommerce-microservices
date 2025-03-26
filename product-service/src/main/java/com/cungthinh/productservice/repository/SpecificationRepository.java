package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cungthinh.productservice.entity.specification.Specification;

@Repository
public interface SpecificationRepository<T extends Specification> extends MongoRepository<T, String> {}
