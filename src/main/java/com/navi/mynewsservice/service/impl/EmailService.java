package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.entity.EmailRequest;
import com.navi.mynewsservice.model.repo.RequestCountRepository;
import com.navi.mynewsservice.model.repo.SubscriberRepository;
import com.navi.mynewsservice.model.schema.RequestCount;
import com.navi.mynewsservice.model.schema.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    RequestCountRepository requestCountRepository;

    public String getHeadlines(String email) {
        List<String> headlines = fetchService.fetchNews(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Top headlines");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Today's important headlines are as follows").append("\n\n");
        for (String str : headlines) { // <-- Added opening curly brace
            bodyBuilder.append("- ").append(str).append("\n");
        } // <-- Added closing curly brace
        message.setText(bodyBuilder.toString());

        // for DB
        RequestCount requestCount = requestCountRepository.findByEmail(email);
        if (requestCount == null) {
            requestCount = new RequestCount();
            requestCount.setEmail(email);
            requestCount.setCount(1);
        } else {
            requestCount.setCount(requestCount.getCount() + 1);
        }
        requestCountRepository.save(requestCount);

        javaMailSender.send(message);
        return "Email sent successfully";
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

            return "Email sent successfully!";
        }


    public List<String> topUserWithMostApiRequest() {
        List<Object[]> topUsersWithCounts = requestCountRepository.findTopUsersWithCounts();
        List<String> topUsersStrings = new ArrayList<>();

        for (Object[] result : topUsersWithCounts) {
            String email = (String) result[0];
            int count = (int) result[1];
            String userString = email + " (" + count + " requests)";
            topUsersStrings.add(userString);
        }

        return topUsersStrings;
    }

    @Autowired
    SubscriberRepository subscriberRepository;

    public String addToScriberList(String email) {
        Subscriber existingSubscriber = subscriberRepository.findByEmail(email);

        if (existingSubscriber != null) {
            return "Subscriber already exists.";
        }
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setEmail(email);
        subscriberRepository.save(newSubscriber);
        return "Subscriber added successfully.";
    }

    public String removeToScriberList(String email) {
        Subscriber existingSubscriber = subscriberRepository.findByEmail(email);
        if (existingSubscriber == null) {
            return "Subscriber not found.";
        }
        subscriberRepository.delete(existingSubscriber);
        return "Subscriber removed successfully.";
    }
}
