package cungthinh.productservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import cungthinh.productservice.entity.specification.Specification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCreationResponse {
    private String id;
    private String name;
    private String category;
    private String imageUri;
    private int quantity;
    private BigDecimal price;
    private Map<String, Object> specs;
}
