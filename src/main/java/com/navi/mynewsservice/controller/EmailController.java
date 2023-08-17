package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.service.impl.EmailService;
import com.navi.mynewsservice.service.impl.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class EmailController {

    private final Logger log = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    EmailService emailService;


    @Autowired
    private final MetricsService metricsService;

    public EmailController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }


    @PostMapping("/send-email/{id}")
    public ResponseEntity<?> sendEmail(@PathVariable String id) {

        metricsService.getCounterWithName("getHeadlinesCounter").labels("/getHeadlinesCounter").inc();
        log.info("Request received to get headlines");
        ResponseEntity<?> callResult = metricsService.getHistogramWithName("getHeadlinesMetricsHistogram").labels("histogram/getHeadlines")
                .time(()-> callHeadlines(id));
        metricsService.getCounterWithNameAndStatusCodes("getHeadlines").labels("/getHeadlines", callResult.getStatusCode().toString()).inc();
        return callResult;
    }

    ResponseEntity<?> callHeadlines(String email) {
        try{
            return new ResponseEntity<>(
                    emailService.getHeadlines(email), HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while getting headlines {}" , e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/top-user")
    public ResponseEntity<?> topUserWithMostApiRequest() {
        try {
            metricsService.getCounterWithNameAndStatusCodes("top_user").labels("top_requesting_users", "200").inc();
            log.info("Received request for top user with most API requests");
            // Your existing logic here
            return new ResponseEntity<>(emailService.topUserWithMostApiRequest(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting top user with most API requests", e);
            metricsService.getCounterWithNameAndStatusCodes("top_user").labels("top_requesting_users", "400").inc();
            // Your existing logic here
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/subscribe/{email}")
    public String isSubscribed(@PathVariable String email) {
        try {
            metricsService.getCounterWithNameAndStatusCodes("subscribe").labels("number_of_subscribed_users","200").inc();
            log.info("Received request to subscribe email: {}", email);
            String response = emailService.addToScriberList(email);
            return response;
        } catch (Exception e) {
            log.error("Error subscribing email: {}", email, e);
            metricsService.getCounterWithNameAndStatusCodes("subscribe").labels("number_of_subscribed_users","400").inc();
            return e.getMessage();
        }
    }

    @DeleteMapping("/unsubscribe/{email}")
    public String unsubscribe(@PathVariable String email) {
        try {
            metricsService.getCounterWithNameAndStatusCodes("subscribe").labels("number_of_unsubscribed_users","200").inc();
            log.info("Received request to unsubscribe email: {}", email);
            return emailService.removeToScriberList(email);
        } catch (Exception e) {
            metricsService.getCounterWithNameAndStatusCodes("subscribe").labels("number_of_unsubscribed_users","400").inc();
            log.error("Error unsubscribing email: {}", email, e);
            return e.getMessage();
        }
    }


}
