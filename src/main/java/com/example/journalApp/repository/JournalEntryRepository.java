package com.example.journalApp.repository;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUser(User user);
    List<JournalEntry> findByUserAndDate(User user, LocalDate date);
}
