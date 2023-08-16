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
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.naming.Context;
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

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Top Headlines - Your Daily News Dose");

            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("<html><body>");
            bodyBuilder.append("<h2>Hello,</h2>");
            bodyBuilder.append("<p>Here are today's top headlines for you:</p>");

            for (int i = 0; i < headlines.size(); i++) {
                String headline = headlines.get(i);
                String color = (i % 2 == 0) ? "#f2f2f2" : "#e6e6e6"; // Alternate colors for text boxes

                bodyBuilder.append("<div style=\"background-color:").append(color).append("; padding: 10px; margin-bottom: 10px;\">");
                bodyBuilder.append("<p>").append(headline).append("</p>");
                bodyBuilder.append("</div>");
            }

            bodyBuilder.append("<p>Stay informed with the latest news from around the world.</p>");
            bodyBuilder.append("<hr>");

            bodyBuilder.append("<h3>Subscription Update:</h3>");
            bodyBuilder.append("<p>Thank you for subscribing to our Daily News Digest. You're currently receiving the latest headlines from your chosen category and country.</p>");
            bodyBuilder.append("<p>If you wish to unsubscribe, <a href=\"[Unsubscribe Link]\">click here</a>.</p>");
            bodyBuilder.append("<p>If you enjoy our service, consider sharing it with your friends and family.</p>");

            bodyBuilder.append("<p>Best regards,<br>The Daily News Digest Team</p>");
            bodyBuilder.append("</body></html>");

            helper.setText(bodyBuilder.toString(), true); // Set the email as HTML

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
        } catch (Exception e) {
            e.printStackTrace();
            return "Email sending failed";
        }
    }





    public String sendWelcomeMail(User user) {

        try {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Our News Service");

            StringBuilder htmlContentBuilder = new StringBuilder();
            htmlContentBuilder.append("<!DOCTYPE html>\n");
            htmlContentBuilder.append("<html>\n<head>\n<style>\n");
            htmlContentBuilder.append("body {\n");
            htmlContentBuilder.append("    font-family: Arial, sans-serif;\n");
            htmlContentBuilder.append("    background-color: #f5f5f5;\n");
            htmlContentBuilder.append("    margin: 0;\n");
            htmlContentBuilder.append("    padding: 0;\n");
            htmlContentBuilder.append("}\n");
            htmlContentBuilder.append(".container {\n");
            htmlContentBuilder.append("    background-color: white;\n");
            htmlContentBuilder.append("    border-radius: 10px;\n");
            htmlContentBuilder.append("    box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);\n");
            htmlContentBuilder.append("    padding: 30px;\n");
            htmlContentBuilder.append("    margin: 50px auto;\n");
            htmlContentBuilder.append("    max-width: 600px;\n");
            htmlContentBuilder.append("}\n");
            htmlContentBuilder.append(".header {\n");
            htmlContentBuilder.append("    color: #3498db;\n");
            htmlContentBuilder.append("    font-size: 24px;\n");
            htmlContentBuilder.append("    text-align: center;\n");
            htmlContentBuilder.append("    margin-bottom: 20px;\n");
            htmlContentBuilder.append("}\n");
            htmlContentBuilder.append(".intro {\n");
            htmlContentBuilder.append("    font-size: 18px;\n");
            htmlContentBuilder.append("    margin-bottom: 20px;\n");
            htmlContentBuilder.append("}\n");
            htmlContentBuilder.append("</style>\n</head>\n<body>\n");
            htmlContentBuilder.append("<div class=\"container\">\n");
            htmlContentBuilder.append("<h2 class=\"header\">Welcome to Our News Service</h2>\n");
            htmlContentBuilder.append("<p class=\"intro\">Dear User,</p>\n");
            htmlContentBuilder.append("<p>We're thrilled to welcome you to our service! Stay up-to-date with the latest news and stories from around the world.</p>\n");
            htmlContentBuilder.append("<p class=\"intro\">Get ready to explore the world through our service!</p>\n");
            htmlContentBuilder.append("<p>If you have any questions or feedback, feel free to reach out to us.</p>\n");
            htmlContentBuilder.append("<div class=\"footer\">\n");
            htmlContentBuilder.append("<p>Best regards,<br>The NewsDose Team</p>\n");
            htmlContentBuilder.append("</div>\n</div>\n</body>\n</html>");

            helper.setText(htmlContentBuilder.toString(), true); // Set HTML content

            javaMailSender.send(message);

            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email.";
        }
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
