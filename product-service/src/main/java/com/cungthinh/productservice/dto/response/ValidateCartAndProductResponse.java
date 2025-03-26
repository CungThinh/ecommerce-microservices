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
public class ValidateCartAndProductResponse {
    private String cartId;
    private List<CartProductResponse> cartProducts;
}
