package com.mindware.workflow.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("workflow.promocred@gmail.com");
        mailSender.setPassword("Start12@");


        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable","true");
        javaMailProperties.put("mail.smtp.auth", "true");
//        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "false");
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}
