package com.navi.mynewsservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/process-response")
    public String processResponse(@RequestBody String response) {
        String recipientEmail = "udemy69learn@gmail.com"; // Replace with the user's email

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);

        if ("YES".equalsIgnoreCase(response)) {
            message.setSubject("Subscription Confirmed");
            message.setText("Thank you for subscribing to our newsletter! You will receive the latest news in your inbox.");
        } else {
            message.setSubject("Unsubscription");
            message.setText("Thank you for your response. You have chosen to unsubscribe from our newsletter.");
        }

        javaMailSender.send(message);

        return "Response email sent successfully.";
    }
}

