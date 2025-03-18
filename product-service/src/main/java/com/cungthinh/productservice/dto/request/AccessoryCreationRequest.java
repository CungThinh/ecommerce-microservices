package com.cungthinh.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryCreationRequest {
    private String brand;
    private String compatibleModels;
    private String color;
    private String material;
}
