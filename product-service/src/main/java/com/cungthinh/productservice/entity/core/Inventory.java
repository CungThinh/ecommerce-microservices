package com.cungthinh.productservice.entity.core;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "inventories")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Data
public class Inventory {
    String productId;

    @Builder.Default
    String location = "HCM";
    int stock;

    @Builder.Default
    List<InventoryReservation> reservations = new ArrayList<>();

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;
}
