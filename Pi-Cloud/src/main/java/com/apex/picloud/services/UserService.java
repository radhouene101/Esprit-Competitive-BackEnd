package com.apex.picloud.services;

import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.utils.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired

    private EmailUtil emailUtil;

    public String forgotPassword(String email) {
        User user = userRepository.findFirstByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("user not found",null);
        }
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("unable to send set password email");
        }
        return "check your email to set password";
    }

    public String setPassword(String email, String newPassword) {
        User user = userRepository.findFirstByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("user not found",null);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));

     //   user.setPassword(newPassword);
        userRepository.save(user);
        return "new password set avec succes";

    }
}
