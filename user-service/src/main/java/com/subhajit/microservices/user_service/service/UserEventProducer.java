package com.subhajit.microservices.user_service.service;

import com.subhajit.microservices.user_service.dto.UserEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEventDTO> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public void sendUserEvent(UserEventDTO event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
