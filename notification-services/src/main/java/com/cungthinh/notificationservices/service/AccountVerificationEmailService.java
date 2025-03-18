package com.cungthinh.notificationservices.service;

import com.cungthinh.event.dto.AccountVerificationEvent;
import com.cungthinh.notificationservices.dto.request.EmailRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AccountVerificationEmailService {
    private final EmailService emailService;

    public AccountVerificationEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "new-user-created", groupId = "account-group")
    public void handleAccountVerification(AccountVerificationEvent event) {
        EmailRequest emailRequest = EmailRequest.builder()
                .to(event.getEmail())
                .subject("Account Verification")
                .body("Your OTP is " + event.getOtp())
                .build();

        emailService.sendSimpleEmail(emailRequest);
    }
}
