package com.cungthinh.notificationservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cungthinh.notificationservices.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {}
