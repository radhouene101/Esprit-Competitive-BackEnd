package com.apex.picloud.dtos;

import com.apex.picloud.models.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String otp;
    private LocalDateTime OtpGeneratedTime;
    private HashSet<Role> roles;



}
