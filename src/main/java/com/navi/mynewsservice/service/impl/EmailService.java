package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.entity.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    FetchService fetchService;

    @Autowired
    JavaMailSender javaMailSender;

    public EmailRequest getHeadlines(String email){

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject("Top headlines");
        emailRequest.setBody("Today's important headlines are as follows");
        List<String> headlines =  fetchService.fetchNews(email);
        emailRequest.setNewsList(headlines);
        return emailRequest;
    }



        public String sendWelcomeMail(User user) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Welcome to Our News Service");

            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("Welcome to Awsome News Site! We're thrilled to have you as part of our community. Get ready to stay updated on the latest news, trends, and stories from around the world.\n\n");
            bodyBuilder.append("Would you like to subscribe to our newsletter and receive the latest news directly in your inbox?\n\n");
            bodyBuilder.append("To subscribe, simply reply to this email with 'YES' in the subject line.");

            message.setText(bodyBuilder.toString());

            javaMailSender.send(message);

            message.setReplyTo("http://localhost:8080/process-response");
            javaMailSender.send(message);
            return "Email sent successfully!";
        }


}