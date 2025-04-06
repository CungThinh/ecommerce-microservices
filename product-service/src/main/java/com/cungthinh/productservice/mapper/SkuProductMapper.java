package com.cungthinh.productservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cungthinh.productservice.dto.response.SkuReponse;
import com.cungthinh.productservice.entity.core.SkuProduct;

@Mapper(componentModel = "spring")
public interface SkuProductMapper {
    List<SkuReponse> toSkuReponseList(List<SkuProduct> skuProducts);
}
