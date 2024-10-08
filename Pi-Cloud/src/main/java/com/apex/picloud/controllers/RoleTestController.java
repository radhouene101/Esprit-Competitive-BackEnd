package com.apex.picloud.controllers;

import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.UserService;
import com.apex.picloud.services.jwt.UserDetailsServiceImpl;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roletest")
public class RoleTestController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @GetMapping("/hello")
    public User hello(){
        UserDetails usersearch = userDetailsService.getAuthenticatedUserDetails();
        String email=usersearch.getUsername();
        User user = userRepository.findFirstByEmail(email);

        return user;
    }



}
