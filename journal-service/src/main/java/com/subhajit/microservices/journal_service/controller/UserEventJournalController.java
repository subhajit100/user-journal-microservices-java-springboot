package com.subhajit.microservices.journal_service.controller;

import com.subhajit.microservices.journal_service.dto.UserEventJournalRequestDTO;
import com.subhajit.microservices.journal_service.model.UserEventJournal;
import com.subhajit.microservices.journal_service.service.UserEventJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
public class UserEventJournalController {

    private final UserEventJournalService journalService;

    // TODO:- make it available to only ADMIN after adding spring security
    @GetMapping
    public ResponseEntity<List<UserEventJournal>> getAllJournals() {
        return ResponseEntity.ok(journalService.getAllJournals());
    }

    // TODO:- make it available to both ADMIN and USER after adding spring security
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserEventJournal>> getJournalsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(journalService.getJournalsByUserId(userId));
    }

    //TODO:- API to be called by Kafka consumer only (internal use)
    @PostMapping
    public ResponseEntity<UserEventJournal> createJournalEntry(@RequestBody UserEventJournalRequestDTO dto) {
        return ResponseEntity.ok(journalService.createJournalEntry(dto));
    }
}
