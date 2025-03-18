package com.cungthinh.productservice.entity.core;

import com.cungthinh.productservice.enums.CartStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "cart")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder
public class Cart {

    @Id
    String id;

    @Builder.Default
    CartStatus status = CartStatus.ACTIVE;

    @Builder.Default
    List<CartProduct> cartProducts = new ArrayList<>();

    @Builder.Default
    int cartProductCount = 0;
    String userId;

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;

    public void setCartProductCount(int count) {
        this.cartProductCount = Math.max(0, count);
    }
}
