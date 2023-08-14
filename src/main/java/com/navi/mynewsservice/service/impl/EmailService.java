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
        message.setSubject("Top Headlines - Your Daily News Dose");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Hello,").append("\n\n");
        bodyBuilder.append("Here are today's top headlines for you:").append("\n\n");

        for (String headline : headlines) {
            bodyBuilder.append("- ").append(headline).append("\n");
        }

        bodyBuilder.append("\n");
        bodyBuilder.append("Stay informed with the latest news from around the world.").append("\n\n");

        bodyBuilder.append("---").append("\n\n");
        bodyBuilder.append("Subscription Update:").append("\n\n");
        bodyBuilder.append("Thank you for subscribing to our Daily News Digest. You're currently receiving the latest headlines from your chosen category and country.").append("\n\n");
        bodyBuilder.append("If you wish to unsubscribe, click here: [Unsubscribe Link]").append("\n\n");
        bodyBuilder.append("If you enjoy our service, consider sharing it with your friends and family.").append("\n\n");
        bodyBuilder.append("Best regards,\nThe Daily News Digest Team");

        message.setText(bodyBuilder.toString());

        // Update request count in the database
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
        bodyBuilder.append("╔══════════════════════╗").append("\n");
        bodyBuilder.append("║      Welcome!        ║").append("\n");
        bodyBuilder.append("╚══════════════════════╝").append("\n\n");

        bodyBuilder.append("Dear User,").append("\n\n");
        bodyBuilder.append("We're thrilled to welcome you to our service!").append("\n");
        bodyBuilder.append("Stay up-to-date with the latest news and stories from around the world.").append("\n\n");

        bodyBuilder.append("Here's a sneak peek at what you can expect:").append("\n");
        bodyBuilder.append("╭──────────────────────────────────────────────────╮").append("\n");
        bodyBuilder.append("│ 1. Daily curated headlines in your inbox         │").append("\n");
        bodyBuilder.append("│ 2. Stay informed about the topics you love       │").append("\n");
        bodyBuilder.append("│ 3. Engaging stories and insights                 │").append("\n");
        bodyBuilder.append("╰──────────────────────────────────────────────────╯").append("\n\n");

        bodyBuilder.append("Get ready to explore the world through our service!").append("\n\n");

        bodyBuilder.append("If you have any questions or feedback, feel free to reach out to us.").append("\n\n");

        bodyBuilder.append("Best regards,").append("\n");
        bodyBuilder.append("The NewsDose Team").append("\n");

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
