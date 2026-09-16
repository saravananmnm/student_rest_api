package com.student.controller;

import com.student.dto.Login_DTO;
import com.student.dto.User_DTO;
import com.student.service.AuthService;
import com.student.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/getAll")
    public List<User_DTO> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/create")
    public User_DTO createUser(@RequestBody User_DTO user_DTO){
        return userService.create(user_DTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Login_DTO loginDto){
        return authService.login(loginDto);
    }

    @PutMapping("/update")
    public User_DTO updateUser(@RequestBody User_DTO user_DTO){
        return userService.update(user_DTO);
    }
}
