package com.example.journalApp.controller;

import com.example.journalApp.model.User;
import com.example.journalApp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.authenticateUser(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/journal";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Show registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        userService.registerUser(user);
        model.addAttribute("success", "Registered successfully! You can now log in.");
        return "register";
    }

    // Logout the user
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
