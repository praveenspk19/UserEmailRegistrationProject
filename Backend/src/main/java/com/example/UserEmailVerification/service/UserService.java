package com.example.UserEmailVerification.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserEmailVerification.model.UserDocES;
import com.example.UserEmailVerification.model.Users;
import com.example.UserEmailVerification.repository.UserRepository;
import com.example.UserEmailVerification.repository.UserSearchRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpService otpService;
    
    @Autowired
    private UserSearchRepository userSearchRepository;

    public ResponseEntity<String> register(String username, String email, String password) {

        if (username == null || username.isBlank() ||
            password == null || password.isBlank() ||
            email == null || !email.contains("@")) {

            return ResponseEntity.badRequest()
                    .body("Invalid username or password or email");
        }

        if (userRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(false);
        user.setCreatedAt(LocalDateTime.now());

        userRepo.save(user);

        saveUserToSearchInES(user);

        otpService.createOtp(email);

        return ResponseEntity.ok("User registered. Verify OTP.");
    }


    public String login(String email, String password) {

        Optional<Users> optional = userRepo.findByEmail(email);

        if (optional.isEmpty()) return "User not found";

        Users user = optional.get();

        if (!user.isVerified()) return "Email not verified";

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials";
        }

        return "Login successful";
    }
    
    public void saveUserToSearchInES(Users user) {

        UserDocES doc = new UserDocES();
        doc.setId(user.getId().toString());
        doc.setUsername(user.getUsername());
        doc.setEmail(user.getEmail());

        userSearchRepository.save(doc);
    }
}
