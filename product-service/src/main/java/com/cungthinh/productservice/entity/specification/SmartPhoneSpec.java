package com.cungthinh.productservice.entity.specification;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Document(collection = "smartphone_specs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SmartPhoneSpec implements Specification {
    String brand;
    String model;
    String storage;
    String screenSize;
    String battery;
}
