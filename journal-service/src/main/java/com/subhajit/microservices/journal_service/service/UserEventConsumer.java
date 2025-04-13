package com.subhajit.microservices.journal_service.service;

import com.subhajit.microservices.journal_service.dto.UserEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserEventConsumer {

    private final UserEventJournalService journalService;

    @KafkaListener(topics = "user-events", groupId = "journal-consumer-group")
    public void listenUserEvents(UserEventDTO event) {
        log.info("Received user event: {}", event);

        journalService.createJournalEntry(event);
    }
}
