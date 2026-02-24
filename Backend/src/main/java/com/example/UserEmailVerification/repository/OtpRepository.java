package com.example.UserEmailVerification.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.UserEmailVerification.model.OtpRequest;

@Repository
public interface OtpRepository extends MongoRepository<OtpRequest,String>{

	Optional<OtpRequest> findTopByEmailOrderByCreatedAtDesc(String email);
	

}
