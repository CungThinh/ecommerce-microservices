package com.cungthinh.productservice.mapper;

import org.mapstruct.Mapper;

import com.cungthinh.productservice.dto.response.SpuResponse;
import com.cungthinh.productservice.entity.core.SpuProduct;

@Mapper(componentModel = "spring")
public interface SpuProductMapper {
    SpuResponse toSpuResponse(SpuProduct spuProduct);
}
