package com.subhajit.microservices.user_service.dto;

import com.subhajit.microservices.user_service.model.Event;
import lombok.*;

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
