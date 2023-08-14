package com.navi.mynewsservice.configuration;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class EmailDigestScheduler {
    @Autowired
    private EmailService emailService;

//    @Autowired
//    private UserService userService;

    @Scheduled(cron = "0 0 9 * * ?") // Run daily at 9:00 AM
    public void sendEmailDigests() {
        //List<User> subscribedUsers = userService.getSubscribedUsers(); // Get subscribed users
        List<User> subscribedUsers = new ArrayList<>();

        for (User user : subscribedUsers) {
            emailService.getHeadlines(user.getEmail()); // Send email digest to each user
        }
    }
}
