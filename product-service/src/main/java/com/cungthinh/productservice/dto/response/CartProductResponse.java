package com.cungthinh.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductResponse {
    private String productId;
    private int quantity;
    private String productName;
    private BigDecimal price;
}
