package com.cungthinh.notificationservices.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cungthinh.notificationservices.dto.request.EmailRequest;
import com.cungthinh.notificationservices.entity.Notification;
import com.cungthinh.notificationservices.enums.NotificationStatus;
import com.cungthinh.notificationservices.repository.NotificationRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    public NotificationService(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void bulkInsertNotification(List<Notification> notifications) {
        for (int i = 0; i < notifications.size(); i++) {
            entityManager.persist(notifications.get(i));
            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    public void sendEmailNotification(EmailRequest emailRequest, Notification notification) {
        emailService.sendSimpleEmail(emailRequest);
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }

    public void sendPushNotification(Notification notification) {
        // send push notification
        notificationRepository.save(notification);
        // add logic later
    }

    public void sendSmsNotification(Notification notification) {
        // send sms notification
    }
}
