package com.example.UserEmailVerification.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.UserEmailVerification.config.RabbitMQConfig;
import com.example.UserEmailVerification.model.OtpRequest;
import com.example.UserEmailVerification.model.OtpTemplate;
import com.example.UserEmailVerification.repository.OtpRepository;
import com.example.UserEmailVerification.repository.UserRepository;

@Service
public class OtpService {
	
	@Autowired
	private OtpRepository otpRepo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
    private StringRedisTemplate redisTemplate;

    private static final long OTP_EXPIRY = 5; // minutes
    private static final int MAX_ATTEMPTS = 3;
	private String generateOtp() {
		Random r=new Random();
		int otp = 100000 + r.nextInt(900000);
		return String.valueOf(otp);
	}
	
	public String createOtp(String email) {
		if (userRepo.findByEmail(email).isEmpty()) {
            return "User Not exists";
		}
		
		
		String otp=generateOtp();
		System.out.println("Otp generated");
		
		OtpRequest otpReq = new OtpRequest();
				otpReq.setEmail(email);
		        otpReq.setOtp(otp);
		        otpReq.setCreatedAt(LocalDateTime.now());
		        otpReq.setExpiresAt(LocalDateTime.now().plusMinutes(5));
		        otpReq.setAttempts(0);
		 otpRepo.save(otpReq);
		// emailService.sendOtpEmail(email, otp);
		 OtpTemplate msg= new OtpTemplate(email,otp);
		 rabbitTemplate.convertAndSend(RabbitMQConfig.OTP_QUEUE, msg);
		 redisTemplate.opsForValue().set(
	                "otp:" + email,
	                otp,
	                OTP_EXPIRY,
	                TimeUnit.MINUTES
	        );

	        // Reset attempts
	        redisTemplate.opsForValue().set(
	                "otp:attempts:" + email,
	                "0",
	                OTP_EXPIRY,
	                TimeUnit.MINUTES
	        );
		 
		 System.out.println("otp created");
		 return "USER REGISTERED , OTP SENT TO MAIL";
	}
	
	public String validateOtp(String email,String userOtp) {
		
		Optional<OtpRequest> optional = otpRepo.findTopByEmailOrderByCreatedAtDesc(email);
		
		String otpKey = "otp:" + email;
	    String attemptKey = "otp:attempts:" + email;
		
		String storedOtpFromRedis = redisTemplate.opsForValue().get(otpKey);
		
		
		if(optional.isEmpty() || storedOtpFromRedis==null)
			return "EXPIRED";
		
		int attempts = Integer.parseInt(redisTemplate.opsForValue().get(attemptKey));
		OtpRequest storedOtpFromDb = optional.get();
		
		if(storedOtpFromDb.getExpiresAt().isBefore(LocalDateTime.now()))
				return "EXPIRED";
		
		if(storedOtpFromDb.getAttempts()>=3) {
			 redisTemplate.delete(otpKey);
	         redisTemplate.delete(attemptKey);
			return "ATTEMPTS EXCEEDED";
		}
		
		if (!storedOtpFromDb.getOtp().equals(userOtp) && !storedOtpFromRedis.equals(userOtp)) {
            storedOtpFromDb.setAttempts(storedOtpFromDb.getAttempts() + 1);
            otpRepo.save(storedOtpFromDb);
            redisTemplate.opsForValue().increment(attemptKey);
            return "INVALID";
        }
		
		userRepo.findByEmail(email).ifPresent(user -> {
		    user.setVerified(true);
		    userRepo.save(user);
		});
		redisTemplate.delete(otpKey);
        redisTemplate.delete(attemptKey);
        return "VERIFIED";
		
	}
	
	
	
	
	
}
