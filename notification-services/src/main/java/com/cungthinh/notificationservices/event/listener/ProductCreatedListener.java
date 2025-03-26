package com.cungthinh.notificationservices.event.listener;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cungthinh.event.dto.ProductCreatedEvent;
import com.cungthinh.notificationservices.dto.response.GetListUserIdsResponse;
import com.cungthinh.notificationservices.entity.Notification;
import com.cungthinh.notificationservices.enums.NotificationType;
import com.cungthinh.notificationservices.repository.feign.UserClient;
import com.cungthinh.notificationservices.service.NotificationService;

@Component
public class ProductCreatedListener {

    private final UserClient userClient;
    private final NotificationService notificationService;

    public ProductCreatedListener(UserClient userClient, NotificationService notificationService) {
        this.userClient = userClient;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "product-created", groupId = "product-group")
    public void handleProductCreatedEvent(ProductCreatedEvent event) {

        GetListUserIdsResponse response = userClient.getAllUserId();
        List<Notification> notificationList = response.getData().stream()
                .map(userId -> Notification.builder()
                        .userId(userId)
                        .message("Chúng tôi vừa cho ra sản phẩm mới " + event.getProductName())
                        .type(NotificationType.PRODUCT_LAUNCH)
                        .build())
                .collect(Collectors.toList());
        notificationService.bulkInsertNotification(notificationList);
        notificationList.forEach(notificationService::sendPushNotification);
    }
}
