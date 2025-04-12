package com.subhajit.microservices.journal_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_event_journal")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserEventJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String username;

    @Column(nullable = false)
    private Event eventType; // CREATE, UPDATE, DELETE, READ etc.

    @Column(nullable = false)
    private Instant timestamp;

    private String payload;
}

