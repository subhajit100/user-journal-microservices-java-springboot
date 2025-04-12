package com.subhajit.microservices.journal_service.repository;

import com.subhajit.microservices.journal_service.model.UserEventJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEventJournalRepository extends JpaRepository<UserEventJournal, Long> {
    List<UserEventJournal> findByUserId(Long userId);
}
