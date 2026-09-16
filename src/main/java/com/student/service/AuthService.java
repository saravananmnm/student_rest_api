package com.student.service;

import com.student.dto.ErrorResponse_DTO;
import com.student.dto.Login_DTO;
import com.student.entity.User;
import com.student.exception.ResourceNotFoundException;
import com.student.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> login(Login_DTO loginDto){
       User user= userService.getByUsername(loginDto.getUsername());
       try {
           System.out.println("User role: " + user.getRole());
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           //UserDetails userDetails = userService.loadUserByUsername(loginDto.getUsername());

           String token =jwtService.generateToken(userDetails);
           System.out.println("===== JWT GENERATED =====");
           System.out.println("Token length: " + token.length());
           return ResponseEntity.ok(new Login_DTO(user.getUsername(),user.getRole().name(),token));
       }catch (BadCredentialsException e){
           return ResponseEntity.ok(new ErrorResponse_DTO("Invalid username or password", HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now()));
       }

    }
}
