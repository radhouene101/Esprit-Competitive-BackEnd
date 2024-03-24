package com.apex.picloud.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roletest")
public class RoleTestController {

    @GetMapping("/hello")

    public String hello(){
        return "hello from user";
    }
}
