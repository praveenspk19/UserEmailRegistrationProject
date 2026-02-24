package com.example.UserEmailVerification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String OTP_QUEUE = "otpQueue";

    @Bean
    public Queue otpQueue() {
        return new Queue(OTP_QUEUE, true);
    }
    
    
   
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
