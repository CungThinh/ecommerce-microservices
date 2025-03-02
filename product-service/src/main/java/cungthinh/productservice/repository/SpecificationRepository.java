package cungthinh.productservice.repository;

import cungthinh.productservice.entity.specification.Specification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationRepository<T extends Specification> extends MongoRepository<T, String> {
}
