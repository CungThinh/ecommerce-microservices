package com.cungthinh.productservice.repository;

import com.cungthinh.productservice.dto.response.ProductResponse;
import com.cungthinh.productservice.entity.core.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findBySlug(String slug);

    @Query(value = "{ isPublished:  true}", fields = "{name:  1, imageUri: 1, price:  1}")
    List<ProductResponse> findAllProductsWithSelectedFields();
}
