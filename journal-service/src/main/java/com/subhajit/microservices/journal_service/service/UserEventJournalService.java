package com.subhajit.microservices.journal_service.service;

import com.subhajit.microservices.journal_service.dto.UserEventDTO;
import com.subhajit.microservices.journal_service.model.UserEventJournal;
import com.subhajit.microservices.journal_service.repository.UserEventJournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEventJournalService {

    private final UserEventJournalRepository journalRepository;

    public List<UserEventJournal> getAllJournals() {
        return journalRepository.findAll();
    }

    public List<UserEventJournal> getJournalsByUserId(Long userId) {
        return journalRepository.findByUserId(userId);
    }

    public void createJournalEntry(UserEventDTO dto) {
        UserEventJournal journal = UserEventJournal.builder()
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .eventType(dto.getEventType())
                .payload(dto.getPayload())
                .timestamp(Instant.now())
                .build();

        journalRepository.save(journal);
    }
}
