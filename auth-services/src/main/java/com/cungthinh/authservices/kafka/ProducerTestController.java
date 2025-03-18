package com.cungthinh.authservices.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProducerTestController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerTestController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaTemplate.send("test-kafka", message);
        return "Message sent: " + message;
    }
}
