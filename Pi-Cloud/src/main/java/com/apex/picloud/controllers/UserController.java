package com.apex.picloud.controllers;

import com.apex.picloud.dtos.AuthentificationRequest;
import com.apex.picloud.dtos.AuthentificationResponse;
import com.apex.picloud.dtos.SignupRequest;
import com.apex.picloud.dtos.UserDTO;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import com.apex.picloud.services.AuthService;
import com.apex.picloud.services.UserService;
import com.apex.picloud.services.jwt.UserDetailsServiceImpl;
import com.apex.picloud.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/emails")
    public List<String> getAllUserEmails() {
        List<User> users = userService.getAllUsers();
        List<String> emails = users.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        return emails;
    }

   /* @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignupRequest registerDto) throws IllegalAccessException {
        return new ResponseEntity<>(userService.register(registerDto), HttpStatus.OK);
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
        UserDTO createdUser= authService.createUser(signupRequest);
        if(createdUser==null)
            return new ResponseEntity<>("user n est pas cree,try later", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String otp) {
        return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
    }
   /* @PutMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthentificationRequest loginDto) {
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }*/
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
       String email=userDetails.getUsername();
       User user = userRepository.findFirstByEmail(email);
       if (!user.isActive()) {
           response.sendError(HttpServletResponse.SC_FORBIDDEN, "User account is inactive. Please contact support.");
           return null;
       }
       final String jwt = jwtUtil.generateToken(userDetails.getUsername());
       return new AuthentificationResponse(jwt);

   }
}
