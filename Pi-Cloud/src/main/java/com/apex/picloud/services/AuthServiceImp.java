package com.apex.picloud.services;

import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.dtos.UserDTO;
import com.apex.picloud.models.Role;
import com.apex.picloud.models.Status;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.RoleRepository;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.utils.EmailUtil;
import com.apex.picloud.utils.OptUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class AuthServiceImp implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;


    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        Role role=roleRepository.findRolesByName("USER");


        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(signupRequest.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }

//        User compareWith = userRepository.findByEmail(signupRequest.getEmail()).get();
//        if(compareWith.getEmail().equals(signupRequest.getEmail())){
//            log(userRepository.findByEmail(signupRequest.getEmail()).get().getEmail());
//            throw new EntityExistsException("a user with the provided email already exist");
//        }

        /*if(userRepository.findByEmail(signupRequest.getEmail()).get().getEmail()!=null){
            throw new EntityExistsException("a user with the provided email already exist");
        }*/


        User user=new User();
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPhone(signupRequest.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setStatus(Status.OFFLINE);
        User createdUser = userRepository.save(user);
        roleService.affectRoleToUser(role.getId(),user.getId());
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
