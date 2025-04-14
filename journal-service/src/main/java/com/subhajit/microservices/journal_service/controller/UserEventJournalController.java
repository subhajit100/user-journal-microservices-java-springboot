package com.subhajit.microservices.journal_service.controller;


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

    @GetMapping
    public ResponseEntity<List<UserEventJournal>> getAllJournals() {
        return ResponseEntity.ok(journalService.getAllJournals());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserEventJournal>> getJournalsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(journalService.getJournalsByUserId(userId));
    }
}
