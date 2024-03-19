package com.apex.picloud.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthentificationResponse {
    private String jwt;
}
