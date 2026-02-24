package com.example.UserEmailVerification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserEmailVerification.model.OtpTemplate;
import com.example.UserEmailVerification.model.Users;
import com.example.UserEmailVerification.service.OtpService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "http://localhost:5173")
public class OtpController {
	
	@Autowired
    private OtpService otpService;
	
	@PostMapping("/resend-otp")
	public ResponseEntity<String> resendOtp(String email) {

	    String result = otpService.createOtp(email);
	    System.out.println("resend");

	    if (result.equals("User Not exists")) {
	    	System.out.println("resent fail");
	        return ResponseEntity.badRequest().body("User not found");
	    }

	    return ResponseEntity.ok("OTP resent successfully");
	}

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody OtpTemplate otpTemp) {

        String result = otpService.validateOtp(otpTemp.getEmail(), otpTemp.getOtp());

        switch (result) {
            case "VERIFIED":
                return ResponseEntity.ok("Account verified");

            case "INVALID":
                return ResponseEntity.badRequest().body("Invalid OTP");

            case "EXPIRED":
                return ResponseEntity.badRequest().body("OTP expired");

            case "ATTEMPTS EXCEEDED":
                return ResponseEntity.status(429).body("Maximum attempts exceeded");

            default:
                return ResponseEntity.badRequest().body("Verification failed");
        }
    }


}
