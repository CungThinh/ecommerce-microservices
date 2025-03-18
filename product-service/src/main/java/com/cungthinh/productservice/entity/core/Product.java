package com.cungthinh.productservice.entity.core;

import com.cungthinh.productservice.entity.specification.Specification;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "products")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder(toBuilder = true)
public class Product {
    @Id
    String id;
    @TextIndexed
    String name;
    String category;
    String imageUri;

    String slug;
    int quantity;
    BigDecimal price;

    @Builder.Default
    Boolean isDraft = true;

    @Builder.Default
    Boolean isPublished = false;

    @DBRef
    Specification specs;

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;
}
