package com.cungthinh.productservice.entity.core;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "inventory_reservations")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@Data
public class InventoryReservation {
    int quantity;
    String cartId;

    @CreatedDate
    Date createdOn;
}
