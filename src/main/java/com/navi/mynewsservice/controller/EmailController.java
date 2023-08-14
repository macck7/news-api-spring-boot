package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.entity.EmailRequest;
import com.navi.mynewsservice.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    EmailService emailService;

    @PostMapping("/send-email/{id}")
    public String sendEmail(@PathVariable String id) {

        EmailRequest emailRequest = emailService.getHeadlines(id);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append(emailRequest.getBody()).append("\n\n");
        for (String str : emailRequest.getNewsList()) {
            bodyBuilder.append("- ").append(str).append("\n");
        }
        message.setText(bodyBuilder.toString());

        javaMailSender.send(message);
        return "Email sent successfully!";
    }
}
