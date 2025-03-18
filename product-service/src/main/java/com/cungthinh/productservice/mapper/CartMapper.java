package com.cungthinh.productservice.mapper;

import com.cungthinh.productservice.dto.response.CartProductResponse;
import com.cungthinh.productservice.dto.response.CartResponse;
import com.cungthinh.productservice.entity.core.Cart;
import com.cungthinh.productservice.entity.core.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toCartResponse(Cart cart);
    CartProductResponse toCartProductResponse(Product product);
}
