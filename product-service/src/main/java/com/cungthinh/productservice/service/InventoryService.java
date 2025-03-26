package com.cungthinh.productservice.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cungthinh.productservice.entity.core.Inventory;
import com.cungthinh.productservice.entity.core.InventoryReservation;
import com.cungthinh.productservice.entity.core.Product;
import com.cungthinh.productservice.exceptions.CustomException;
import com.cungthinh.productservice.exceptions.ErrorCode;
import com.cungthinh.productservice.repository.InventoryRepository;
import com.cungthinh.productservice.repository.InventoryReservationRepository;
import com.mongodb.client.result.UpdateResult;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryReservationRepository inventoryReservationRepository;
    private final MongoTemplate mongoTemplate;

    public InventoryService(
            InventoryRepository inventoryRepository,
            InventoryReservationRepository inventoryReservationRepository,
            MongoTemplate mongoTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryReservationRepository = inventoryReservationRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void createProductInventory(Product product) {
        Inventory inventory = Inventory.builder()
                .productId(product.getId())
                .stock(product.getQuantity())
                .build();

        inventoryRepository.save(inventory);
    }

    public boolean reserveInventory(String productId, int quantity, String cartId) {
        Query query =
                new Query(Criteria.where("productId").is(productId).and("stock").gte(quantity));
        Update update = new Update();
        update.inc("stock", -quantity);

        InventoryReservation inventoryReservation =
                InventoryReservation.builder().cartId(cartId).quantity(quantity).build();
        inventoryReservationRepository.save(inventoryReservation);

        update.push("reservations", inventoryReservation);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Inventory.class);
        return result.getModifiedCount() == 1;
    }

    public boolean isStockAvailable(String productId, int quantity) {
        Inventory inventory = inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVENTORY_NOT_FOUND));
        return inventory.getStock() >= quantity;
    }
}
