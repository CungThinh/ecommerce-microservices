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
public class ProductVariantsRequest {
    private List<String> images;
    private String name;
    private List<String> options;
}
