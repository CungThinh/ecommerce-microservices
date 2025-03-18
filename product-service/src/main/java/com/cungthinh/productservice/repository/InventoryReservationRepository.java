package com.cungthinh.productservice.repository;

import com.cungthinh.productservice.entity.core.InventoryReservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryReservationRepository extends MongoRepository<InventoryReservation, String> {
}
