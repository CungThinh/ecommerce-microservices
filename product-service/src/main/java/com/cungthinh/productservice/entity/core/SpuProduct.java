package com.cungthinh.productservice.entity.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cungthinh.productservice.dto.request.ProductAttribRequest;
import com.cungthinh.productservice.dto.request.ProductVariantsRequest;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Document(collection = "spu_products")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder(toBuilder = true)
public class SpuProduct {
    @Id
    String id;

    String productId;
    String name;
    String slug;
    String description;
    BigDecimal price;

    @Builder.Default
    List<String> category = new ArrayList<>();

    @Builder.Default
    List<ProductAttribRequest> productAttributes = new ArrayList<>();

    @Builder.Default
    List<ProductVariantsRequest> productVariants = new ArrayList<>();

    @Builder.Default
    Boolean isDraft = true;

    @Builder.Default
    Boolean isPublished = false;

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;
}
