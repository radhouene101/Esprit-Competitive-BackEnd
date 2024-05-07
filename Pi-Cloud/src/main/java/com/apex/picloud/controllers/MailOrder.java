package com.apex.picloud.controllers;


import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.dtos.UserDTO;
import com.apex.picloud.utils.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MailOrder")
public class MailOrder {
    @Autowired
    private EmailUtil emailUtil;

    @PostMapping("/send/{email}")
    public void sendmail( @PathVariable String email) throws MessagingException {
       emailUtil.sendMailOrder(email);
    }
}
