package cungthinh.productservice.entity.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "charger_specs")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerSpec implements Specification {
    String brand;
    String powerOutput;
    String cableType;
    String length;
    String fastCharging;
}
