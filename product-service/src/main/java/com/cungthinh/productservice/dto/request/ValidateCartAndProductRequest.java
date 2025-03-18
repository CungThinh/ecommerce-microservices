package com.cungthinh.productservice.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCartAndProductRequest {
    private String cartId;

    @Valid
    private List<CartProduct> cartProducts;
}
