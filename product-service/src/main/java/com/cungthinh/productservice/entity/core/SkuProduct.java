package com.cungthinh.productservice.entity.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Document(collection = "sku_products")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder(toBuilder = true)
public class SkuProduct {
    @Id
    String id;

    @Indexed(unique = true)
    String skuId;

    @Builder.Default
    String slug = "";

    @Builder.Default
    Integer sort = 0;

    @Builder.Default
    Boolean skuDefault = false;

    @Builder.Default
    List<Integer> skuTierIndex = new ArrayList<>();

    @Builder.Default
    int stock = 0;

    String productId;
    BigDecimal price;

    @Builder.Default
    Boolean isDraft = true;

    @Builder.Default
    Boolean isPublished = false;
}
