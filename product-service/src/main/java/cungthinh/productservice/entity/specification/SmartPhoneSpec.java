package cungthinh.productservice.entity.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PRIVATE;

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
