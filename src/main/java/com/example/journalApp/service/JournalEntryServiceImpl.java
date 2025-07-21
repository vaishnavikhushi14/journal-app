package com.example.journalApp.service;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import com.example.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Override
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    @Override
    public List<JournalEntry> getEntriesByDate(User user, LocalDate date) {
        return journalEntryRepository.findByUserAndDate(user, date);
    }

    @Override
    public List<JournalEntry> getEntriesSorted(User user, boolean descending) {
        List<JournalEntry> entries = journalEntryRepository.findByUser(user);
        return entries.stream()
                .sorted(descending
                        ? Comparator.comparing(JournalEntry::getDate).reversed()
                        : Comparator.comparing(JournalEntry::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public JournalEntry getEntryById(Long id) {
        return journalEntryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteEntryById(Long id) {
        journalEntryRepository.deleteById(id);
    }
}
