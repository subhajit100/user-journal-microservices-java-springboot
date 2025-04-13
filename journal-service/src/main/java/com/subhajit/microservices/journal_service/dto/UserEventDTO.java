package com.subhajit.microservices.journal_service.dto;

import com.subhajit.microservices.journal_service.model.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEventDTO {
    private Long userId;
    private String username;
    private Event eventType;
    private String payload;
}
