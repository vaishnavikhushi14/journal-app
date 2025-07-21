package com.example.journalApp.controller;

import com.example.journalApp.model.JournalEntry;
import com.example.journalApp.model.User;
import com.example.journalApp.service.JournalEntryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public String viewJournal(@RequestParam(required = false) String date,
                              @RequestParam(defaultValue = "desc") String sort,
                              HttpSession session,
                              Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<JournalEntry> entries;
        if (date != null && !date.isEmpty()) {
            LocalDate filterDate = LocalDate.parse(date);
            entries = journalEntryService.getEntriesByDate(user, filterDate);
        } else {
            boolean descending = sort.equalsIgnoreCase("desc");
            entries = journalEntryService.getEntriesSorted(user, descending);
        }

        model.addAttribute("entries", entries);
        model.addAttribute("username", user.getUsername());
        return "entries";
    }

    @GetMapping("/new")
    public String newEntryForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("journalEntry", new JournalEntry());
        return "new_entry";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute JournalEntry journalEntry,
                           HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        journalEntry.setUser(user);
        journalEntryService.saveEntry(journalEntry);
        return "redirect:/journal";
    }

    @GetMapping("/edit/{id}")
    public String editEntryForm(@PathVariable Long id,
                                HttpSession session,
                                Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        JournalEntry entry = journalEntryService.getEntryById(id);
        if (entry == null || !entry.getUser().getId().equals(user.getId())) {
            return "redirect:/journal";
        }

        model.addAttribute("journalEntry", entry);
        return "new_entry";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id,
                              HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        JournalEntry entry = journalEntryService.getEntryById(id);
        if (entry != null && entry.getUser().getId().equals(user.getId())) {
            journalEntryService.deleteEntryById(id);
        }

        return "redirect:/journal";
    }
}
