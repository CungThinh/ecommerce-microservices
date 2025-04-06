package com.cungthinh.productservice.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkuReponse {
    private String id;
    private String skuId;
    private String slug;
    private Integer sort;
    private List<Integer> skuTierIndex;
    private Boolean skuDefault;
    private Integer stock;
    private BigDecimal price;
    private String productId;
}
