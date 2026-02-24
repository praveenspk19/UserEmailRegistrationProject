package com.example.UserEmailVerification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserEmailVerification.model.Users;
import com.example.UserEmailVerification.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔹 Register User
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
    	
       return  userService.register(user.getUsername(), user.getEmail(), user.getPassword());

    }

    // 🔹 Login User
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {

        String response = userService.login(
                user.getEmail(),
                user.getPassword()
        );
        return ResponseEntity.ok(response);
    }
}
