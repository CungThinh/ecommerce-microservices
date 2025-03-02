package cungthinh.productservice.service.specification;

import cungthinh.productservice.entity.specification.AccessorySpec;
import cungthinh.productservice.entity.specification.SmartPhoneSpec;
import cungthinh.productservice.entity.specification.Specification;
import cungthinh.productservice.repository.AccessorySpecRepository;
import cungthinh.productservice.repository.SmartPhoneSpecRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class SpecificationService {
    private final AccessorySpecRepository accessorySpecRepository;
    private final SmartPhoneSpecRepository smartPhoneSpecRepository;

    private final Map<String, Function<Map<String, Object>, Specification>> specificationRegistry = new HashMap<>();

    public SpecificationService(
            AccessorySpecRepository accessorySpecRepository,
            SmartPhoneSpecRepository smartPhoneSpecRepository
    ) {
        this.accessorySpecRepository = accessorySpecRepository;
        this.smartPhoneSpecRepository = smartPhoneSpecRepository;
        specificationRegistry.put("Accessory", this::createAccessorySpec);
        specificationRegistry.put("Smartphone", this::createSmartPhoneSpec);
    }

    public Specification createSpecification(String category, Map<String, Object> data) {
        Function<Map<String, Object>, Specification> creatorFunction = specificationRegistry.get(category);
        if (creatorFunction == null) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        return creatorFunction.apply(data);
    }

    private AccessorySpec createAccessorySpec(Map<String, Object> data) {
        AccessorySpec newAccessory = AccessorySpec.builder()
                .brand((String) data.get("brand"))
                .color((String) data.get("color"))
                .compatibleModels((String) data.get("compatibleModels"))
                .material((String) data.get("material"))
                .build();
        accessorySpecRepository.save(newAccessory);
        return newAccessory;
    }

    private SmartPhoneSpec createSmartPhoneSpec(Map<String, Object> data) {
        SmartPhoneSpec newSmartPhone = SmartPhoneSpec.builder()
                .brand((String) data.get("brand"))
                .model((String) data.get("model"))
                .storage((String) data.get("storage"))
                .screenSize((String) data.get("screenSize"))
                .battery((String) data.get("battery"))
                .build();
        smartPhoneSpecRepository.save(newSmartPhone);
        return newSmartPhone;
    }
}


// public class SpecificationFactoryRegistry {
//    // Map of category name to specification class and repository
//    private final Map<String, SpecificationTypeConfig<?>> factoryMap = new HashMap<>();
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    // Constructor injection of all repositories
//    public SpecificationFactoryRegistry(
//            AccessorySpecRepository accessoryRepo,
//            SmartphoneSpecRepository smartphoneRepo,
//            // Other repositories as needed
//    ) {
//        // Register each type
//        register("Accessory", AccessorySpec.class, accessoryRepo);
//        register("Smartphone", SmartphoneSpec.class, smartphoneRepo);
//        // Add more types here as needed
//    }
//
//    private <T extends Specification> void register(
//            String category,
//            Class<T> specClass,
//            MongoRepository<T, String> repository) {
//        factoryMap.put(category, new SpecificationTypeConfig<>(specClass, repository));
//    }
//
//    public Specification createSpecification(String category, Map<String, Object> data) {
//        SpecificationTypeConfig<?> config = factoryMap.get(category);
//        if (config == null) {
//            throw new IllegalArgumentException("Unknown category: " + category);
//        }
//        return config.createAndSave(data, objectMapper);
//    }
//
//    // Helper class to store type configuration
//    private static class SpecificationTypeConfig<T extends Specification> {
//        private final Class<T> specClass;
//        private final MongoRepository<T, String> repository;
//
//        public SpecificationTypeConfig(Class<T> specClass, MongoRepository<T, String> repository) {
//            this.specClass = specClass;
//            this.repository = repository;
//        }
//
//        public Specification createAndSave(Map<String, Object> data, ObjectMapper mapper) {
//            T spec = mapper.convertValue(data, specClass);
//            return repository.save(spec);
//        }
//    }
//}