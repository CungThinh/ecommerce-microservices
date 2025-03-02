package cungthinh.productservice.mapper;

import cungthinh.productservice.dto.response.ProductCreationResponse;
import cungthinh.productservice.dto.response.ProductResponse;
import cungthinh.productservice.entity.core.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SpecificationMapper.class})
public interface ProductMapper {

    @Mapping(source = "specs", target = "specs")
    ProductCreationResponse toProductCreationResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);

    ProductResponse toProductResponse(Product product);
}
