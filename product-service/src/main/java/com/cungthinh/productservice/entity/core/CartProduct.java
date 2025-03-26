package com.cungthinh.productservice.entity.core;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Document(collection = "cart_products")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder
public class CartProduct {
    @Id
    String id;

    String productId;
    int quantity;
    String productName;
    BigDecimal price;

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;
}
