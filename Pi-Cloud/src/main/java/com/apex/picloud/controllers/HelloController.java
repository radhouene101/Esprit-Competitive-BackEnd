package com.apex.picloud.controllers;

import com.apex.picloud.dtos.HelloResponse;
import com.apex.picloud.services.jwt.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @GetMapping("/hello")
    public HelloResponse hello() {
        UserDetails user = userDetailsService.getAuthenticatedUserDetails();

    //  UserDetails user=  userDetailsService.loadUserByUsername( userDetails.getUsername());
        // Récupérer les détails de l'utilisateur connecté
        //String username = userDetails.getUsername(); // Nom d'utilisateur
        return new HelloResponse("Hello from JWT Authorization. Username: " + user.getUsername()+user.getPassword()+user.get );
    }
}
