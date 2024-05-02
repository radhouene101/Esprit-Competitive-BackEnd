package com.apex.picloud.services;

import com.apex.picloud.dtos.AuthentificationRequest;
import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.models.Role;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.RoleRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.utils.EmailUtil;
import com.apex.picloud.utils.OptUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired

    private EmailUtil emailUtil;
    @Autowired
    private OptUtil otpUtil;
    @Autowired
    RoleRepository roleRepository;


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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String register(SignupRequest registerDto) throws IllegalAccessException {
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        if(userRepository.findByEmail(registerDto.getEmail()).get().getEmail()!=null){
            throw  new IllegalAccessException("user with the provided email already exist");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User registration successful";
    }

    public String verifyAccount(String email, String otp) {
        User user = userRepository.findFirstByEmail(email);
               // .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 2000)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        User user = userRepository.findFirstByEmail(email);
               // .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    public String login(AuthentificationRequest loginDto) {
        User user = userRepository.findFirstByEmail(loginDto.getEmail());
              //  .orElseThrow(
                  //      () -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
        if (!loginDto.getPassword().equals(user.getPassword())) {
            return "Password is incorrect";
        } else if (!user.isActive()) {
            return "your account is not verified";
        }
        return "Login successful";
    }



}
