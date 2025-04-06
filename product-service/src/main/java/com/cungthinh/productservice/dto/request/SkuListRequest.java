package com.cungthinh.productservice.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuListRequest {
    private List<Integer> skuTierIndex;
    private BigDecimal price;
    private Integer stock;
}
