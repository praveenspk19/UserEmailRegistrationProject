package com.example.UserEmailVerification.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.UserEmailVerification.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users,String>{
	
	Optional<Users> findByEmail(String email);


}
