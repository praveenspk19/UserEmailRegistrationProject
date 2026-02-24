package com.example.UserEmailVerification.service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.UserEmailVerification.config.RabbitMQConfig;
import com.example.UserEmailVerification.model.OtpTemplate;

@Service
public class otpConsumer {
	
	@Autowired
    private EmailService emailService;

   

	@RabbitListener(queues = RabbitMQConfig.OTP_QUEUE)
	public void receiveOtpMessage(OtpTemplate message) {

	    try {
	        emailService.sendOtpEmail(
	                message.getEmail(),
	                message.getOtp()
	        );
	        System.out.println("otp send");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
