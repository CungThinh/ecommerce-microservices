package com.cungthinh.productservice.mapper;

import com.cungthinh.productservice.dto.response.ProductCreationResponse;
import com.cungthinh.productservice.dto.response.ProductResponse;
import com.cungthinh.productservice.entity.core.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SpecificationMapper.class})
public interface ProductMapper {

    @Mapping(source = "specs", target = "specs")
    ProductCreationResponse toProductCreationResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);

    ProductResponse toProductResponse(Product product);
}
