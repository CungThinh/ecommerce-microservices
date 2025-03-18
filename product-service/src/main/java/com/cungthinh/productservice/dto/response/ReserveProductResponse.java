package com.cungthinh.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductResponse {
    @Builder.Default
    private Map<String, Boolean> productReservationStatus = new HashMap<>();

    public void addProductStatus(String productId, boolean isReserved) {
        productReservationStatus.put(productId, isReserved);
    }
}
