package com.cungthinh.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cungthinh.productservice.entity.core.InventoryReservation;

public interface InventoryReservationRepository extends MongoRepository<InventoryReservation, String> {}
