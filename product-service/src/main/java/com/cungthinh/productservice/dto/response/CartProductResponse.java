package com.cungthinh.productservice.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
