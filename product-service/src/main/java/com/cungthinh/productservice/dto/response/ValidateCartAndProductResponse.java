package com.cungthinh.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCartAndProductResponse {
    private String cartId;
    private List<CartProductResponse> cartProducts;
}
