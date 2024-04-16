package com.apex.picloud.controllers;

import com.apex.picloud.dtos.AuthentificationRequest;
import com.apex.picloud.dtos.AuthentificationResponse;
import com.apex.picloud.services.jwt.UserDetailsServiceImpl;
import com.apex.picloud.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthentificationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
@PostMapping("/authentication")
@SendTo("/user/public")
    public AuthentificationResponse createAuthentificationToken(@RequestBody AuthentificationRequest authentificationRequest, HttpServletResponse response)throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
try{
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationRequest.getEmail(),authentificationRequest.getPassword()));
}catch (BadCredentialsException e){
    throw new BadCredentialsException("incorrect");
}catch (    DisabledException disabledException){
    response.sendError(HttpServletResponse.SC_NOT_FOUND,"user not created,register user first");
    return null;

}
//Auth success
final UserDetails userDetails = userDetailsService.loadUserByUsername(authentificationRequest.getEmail());

final String jwt = jwtUtil.generateToken(userDetails.getUsername());
return new AuthentificationResponse(jwt);

    }

}
