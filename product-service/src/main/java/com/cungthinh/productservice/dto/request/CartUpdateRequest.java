package com.cungthinh.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequest {
    private String userId;
    private String productId;
    private Integer oldQuantity;
    private Integer quantity;
}
