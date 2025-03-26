package com.cungthinh.productservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cungthinh.productservice.dto.response.ProductCreationResponse;
import com.cungthinh.productservice.dto.response.ProductResponse;
import com.cungthinh.productservice.entity.core.Product;

@Mapper(
        componentModel = "spring",
        uses = {SpecificationMapper.class})
public interface ProductMapper {

    @Mapping(source = "specs", target = "specs")
    ProductCreationResponse toProductCreationResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);

    ProductResponse toProductResponse(Product product);
}
