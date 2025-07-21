package com.example.journalApp.service;

import com.example.journalApp.model.User;

public interface UserService {
    void registerUser(User user);

    boolean usernameExists(String username);

    User authenticateUser(String username, String password);
}
