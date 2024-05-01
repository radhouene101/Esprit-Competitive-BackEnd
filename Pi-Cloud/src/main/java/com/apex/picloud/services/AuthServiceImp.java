package com.apex.picloud.services;

import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.dtos.UserDTO;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.utils.EmailUtil;
import com.apex.picloud.utils.OptUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImp implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
//        if(userRepository.findByEmail(signupRequest.getEmail()).get().getEmail()!=null){
//            throw new EntityExistsException("a user with the provided email already exist");
//        }

        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(signupRequest.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }

        User user=new User();
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPhone(signupRequest.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO=new UserDTO();
        userDTO.setOtp(createdUser.getOtp());
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        userDTO.setPhone(createdUser.getPhone());
        userDTO.setPassword(createdUser.getPassword());
        userDTO.setOtpGeneratedTime(createdUser.getOtpGeneratedTime());
        return userDTO;
    }

}
