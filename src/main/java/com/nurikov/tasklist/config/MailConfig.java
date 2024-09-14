package com.nurikov.tasklist.config;

import com.nurikov.tasklist.service.props.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@RequiredArgsConstructor
public class MailConfig {
    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setJavaMailProperties(mailProperties.getProperties());
        mailSender.getJavaMailProperties();
        return mailSender;
    }
}
