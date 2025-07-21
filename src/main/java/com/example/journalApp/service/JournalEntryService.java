package com.example.journalApp.service;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;

import java.time.LocalDate;
import java.util.List;

public interface JournalEntryService {
    void saveEntry(JournalEntry journalEntry);

    List<JournalEntry> getEntriesByDate(User user, LocalDate date);

    List<JournalEntry> getEntriesSorted(User user, boolean descending);

    JournalEntry getEntryById(Long id);

    void deleteEntryById(Long id);
}
