package com.cungthinh.productservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseV2 {
    private SpuResponse spuInfo;
    private List<SkuReponse> skuList;
}
