package cungthinh.productservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.result.UpdateResult;
import cungthinh.productservice.dto.request.ProductCreationRequest;
import cungthinh.productservice.dto.response.ProductCreationResponse;
import cungthinh.productservice.dto.response.ProductResponse;
import cungthinh.productservice.entity.core.Product;
import cungthinh.productservice.entity.specification.Specification;
import cungthinh.productservice.mapper.ProductMapper;
import cungthinh.productservice.repository.ProductRepository;
import cungthinh.productservice.service.specification.SpecificationService;
import cungthinh.productservice.utils.SlugGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoClient mongo;

    public ProductCreationResponse createProduct(ProductCreationRequest request) {

        Specification specs = specificationService.createSpecification(request.getCategory(), request.getSpecs());

        Product newProduct = Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .specs(specs)
                .imageUri(request.getImageUri())
                .quantity(request.getQuantity())
                .build();

        String productSlug = SlugGenerator.toSlug(request.getName());
        newProduct.setSlug(productSlug);

        productRepository.save(newProduct);
        return productMapper.toProductCreationResponse(newProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllProductsWithSelectedFields();
    }

    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow();
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllDraftProducts() {
        Query query = new Query();
        query.addCriteria(Criteria.where("isDraft").is(true));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return productMapper.toProductResponseList(products);
    }

    public int publishOrUnPublishProduct(String id, boolean publish) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        if(publish) {
            update.set("isPublished", true);
            update.set("isDraft", false);
        } else {
            update.set("isPublished", false);
            update.set("isDraft", true);
        }
        UpdateResult result = mongoTemplate.updateFirst(query, update, Product.class);
        log.info("Count {}", result.getModifiedCount());
        return (int) result.getModifiedCount();
    }

    public List<ProductResponse> searchProduct(String keyword) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(keyword);
        Query query = TextQuery.queryText(textCriteria).sortByScore()
                .addCriteria(Criteria.where("isPublished").is(true));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return productMapper.toProductResponseList(products);
    }
}
