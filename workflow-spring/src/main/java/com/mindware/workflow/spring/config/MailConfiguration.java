package com.mindware.workflow.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration

public class MailConfiguration {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.properties.mail.smtp.socketFactory.port}")
    private Integer port;
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String tls;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);


        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable",tls);
        javaMailProperties.put("mail.smtp.auth", auth);
//        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "false");
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}
