package com.apex.picloud.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String otp;
    private LocalDateTime OtpGeneratedTime;



}
