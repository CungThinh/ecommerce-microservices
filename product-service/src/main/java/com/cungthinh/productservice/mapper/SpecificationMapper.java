package com.cungthinh.productservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cungthinh.productservice.entity.specification.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpecificationMapper {
    private final ObjectMapper objectMapper;

    public SpecificationMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> toMap(Specification specification) {
        if (specification == null) {
            return null;
        }
        Map<String, Object> map = objectMapper.convertValue(specification, Map.class);
        map.remove("id");
        return map;
    }
}
