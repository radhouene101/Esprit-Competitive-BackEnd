package com.apex.picloud.services;

import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.dtos.UserDTO;

public interface AuthService {
UserDTO createUser(SignupRequest signupRequest);
}
