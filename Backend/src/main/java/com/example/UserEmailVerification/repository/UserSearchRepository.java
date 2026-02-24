package com.example.UserEmailVerification.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.UserEmailVerification.model.UserDocES;

import java.util.List;

public interface UserSearchRepository
        extends ElasticsearchRepository<UserDocES, String> {

    List<UserDocES> findByUsernameContaining(String username);
}
