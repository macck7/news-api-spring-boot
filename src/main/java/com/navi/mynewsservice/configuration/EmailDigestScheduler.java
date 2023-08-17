package com.navi.mynewsservice.configuration;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.model.repo.SubscriberRepository;
import com.navi.mynewsservice.model.schema.Subscriber;
import com.navi.mynewsservice.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class EmailDigestScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriberRepository subscriberRepository;


    @Scheduled(cron = "0 0 9 * * ?") // Run daily at 9:00 AM
    //@Scheduled(fixedDelay = 1000)
    @Transactional
    public void sendEmailDigests() {
        System.out.println("Started");
      //  List<User> subscribedUsers = userService.getSubscribedUsers(); // Get subscribed users
        List<Subscriber> subscribedUsers = subscriberRepository.findAll();

        for (Subscriber user : subscribedUsers) {
            emailService.getHeadlines(user.getEmail()); // Send email digest to each user
        }
    }
}
