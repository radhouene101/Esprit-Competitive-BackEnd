package com.apex.picloud.controllers;

import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.dtos.UserDTO;
import com.apex.picloud.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
        UserDTO createdUser= authService.createUser(signupRequest);
        if(createdUser==null)
            return new ResponseEntity<>("user n est pas cree,try later", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }
}
