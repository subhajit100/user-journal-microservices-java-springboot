package com.subhajit.microservices.journal_service.dto;

import com.subhajit.microservices.journal_service.model.Event;
import lombok.Data;

@Data
public class UserEventJournalRequestDTO {
    private Long userId;
    private String username;
    private Event eventType;
    private String payload;
}
