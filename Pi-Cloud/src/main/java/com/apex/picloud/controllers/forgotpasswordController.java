package com.apex.picloud.controllers;

import com.apex.picloud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class forgotpasswordController {
    @Autowired
    private UserService userService;


    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestHeader String newPassword){
        return new ResponseEntity<>(userService.setPassword(email,newPassword),HttpStatus.OK);
    }
}
