package com.cungthinh.productservice.mapper;

import org.mapstruct.Mapper;

import com.cungthinh.productservice.dto.response.CartProductResponse;
import com.cungthinh.productservice.dto.response.CartResponse;
import com.cungthinh.productservice.entity.core.Cart;
import com.cungthinh.productservice.entity.core.Product;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toCartResponse(Cart cart);

    CartProductResponse toCartProductResponse(Product product);
}
