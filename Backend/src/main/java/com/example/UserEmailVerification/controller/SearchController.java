package com.example.UserEmailVerification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserEmailVerification.model.UserDocES;
import com.example.UserEmailVerification.repository.UserSearchRepository;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    @Autowired
    private UserSearchRepository userSearchRepository;

    @GetMapping("/users")
    public List<UserDocES> searchUsers(
            @RequestParam String username) {

        return userSearchRepository
                .findByUsernameContaining(username);
    }
}
