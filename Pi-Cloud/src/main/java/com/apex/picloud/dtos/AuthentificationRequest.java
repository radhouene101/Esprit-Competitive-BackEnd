package com.apex.picloud.dtos;

import lombok.Data;

@Data
public class AuthentificationRequest {
    private String email;
    private String password;
}
