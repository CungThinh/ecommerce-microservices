package com.cungthinh.productservice.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.cungthinh.productservice.dto.request.ProductAttribRequest;
import com.cungthinh.productservice.dto.request.ProductVariantsRequest;
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
public class SpuResponse {
    private String id;
    private String productId;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;

    List<String> category;
    List<ProductAttribRequest> productAttributes;
    List<ProductVariantsRequest> productVariants;
}
