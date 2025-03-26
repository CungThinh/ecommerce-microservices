package com.cungthinh.notificationservices.event.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cungthinh.event.dto.AccountVerificationEvent;
import com.cungthinh.notificationservices.dto.request.EmailRequest;
import com.cungthinh.notificationservices.entity.Notification;
import com.cungthinh.notificationservices.enums.NotificationStatus;
import com.cungthinh.notificationservices.enums.NotificationType;
import com.cungthinh.notificationservices.service.NotificationService;

@Component
public class NewAccountListener {
    private final NotificationService notificationService;

    public NewAccountListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "new-user-created", groupId = "account-group")
    public void handleAccountVerification(AccountVerificationEvent event) {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(event.getEmail())
                .subject("Account Verification")
                .body("Your OTP is " + event.getOtp())
                .build();

        Notification notification = Notification.builder()
                .type(NotificationType.NEW_ACCOUNT_CREATED)
                .message("New account created")
                .userId(event.getUserId())
                .status(NotificationStatus.PENDING)
                .build();

        notificationService.sendEmailNotification(emailRequest, notification);
    }
}
