package com.example.UserEmailVerification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("OTP Verification");
            message.setText(
                    "Dear User,\n\n" +
                    "Your OTP for account verification is: " + otp +
                    "\n\nThis OTP will expire in 5 minutes.\n\n" +
                    "Regards,\nEmail Verification Application"
            );

            mailSender.send(message);
            System.out.println("__--------otp send----------");

        } catch (MailException e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }
}
