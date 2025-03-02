package cungthinh.productservice.entity.specification;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accessory_specs")
public class AccessorySpec implements Specification {
    @Id
    private String id;

    private String brand;
    private String compatibleModels;
    private String color;
    private String material;
}
