package com.cungthinh.authservices.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerTest {
    @KafkaListener(topics = "test-kafka", groupId = "my-group")
    public void consume(String message) {
        log.info("Received message: {}", message);
    }
}
