package com.apex.picloud.controllers;

import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/emails")
    public List<String> getAllUserEmails() {
        List<User> users = userService.getAllUsers();
        List<String> emails = users.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        return emails;
    }
}
