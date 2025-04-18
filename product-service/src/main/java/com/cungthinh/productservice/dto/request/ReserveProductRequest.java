package com.cungthinh.productservice.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductRequest {
    private String cartId;
    private List<CartProduct> cartProducts;
}
