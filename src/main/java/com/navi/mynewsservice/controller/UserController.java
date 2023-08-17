package com.navi.mynewsservice.controller;
import com.navi.mynewsservice.service.impl.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.model.repo.ApiCallRecordRepo;
import com.navi.mynewsservice.model.schema.ApiCallRecord;
import com.navi.mynewsservice.service.impl.EmailService;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import com.navi.mynewsservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private ApiCallRecordRepo apiCallRecordRepo;
    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    EmailService emailService;

    @Autowired
    private final MetricsService metricsService;

    public UserController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUserDetails(@RequestBody User user) {
        try {
            long startTime = System.currentTimeMillis();
            String response = newsService.addUserDetails(
                    user.getEmail(),
                    user.getCountry(),
                    user.getCategory(),
                    user.getSources());
            long endTime = System.currentTimeMillis();
            long tt = endTime - startTime;

            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", response, tt));
            emailService.sendWelcomeMail(user);

            logger.info("Added user details: {}", user.getEmail());
            metricsService.getCounterWithName("add_user").labels("add_user_count").inc();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Long tt = 0L;
            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", e.getMessage(), tt));

            logger.error("Error adding user details: {}", user.getEmail(), e);

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allcountry")
    public ResponseEntity<?> getAllcountries() {
        logger.info("Received request to get all countries");

        return new ResponseEntity<>(userService.getAllCountries(), HttpStatus.OK);
    }

    @GetMapping("/allcategory")
    public ResponseEntity<List<String>> getAllCategories() {
        logger.info("Received request to get all categories");
        return new ResponseEntity<>(userService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/addcountry")
    public ResponseEntity<?> addCountry(@RequestParam String country) {
        userService.addCountry(country);
        logger.info("Added country: {}", country);
        metricsService.getCounterWithName("add_country").labels("add_country_count").inc();
        return new ResponseEntity<>("Country added successfully", HttpStatus.OK);
    }

    @GetMapping("/addcategory")
    public ResponseEntity<String> addCategory(@RequestParam String category) {
        userService.addCategory(category);
        logger.info("Added category: {}", category);
        metricsService.getCounterWithName("add_category").labels("add_category_count").inc();
        return new ResponseEntity<>("Category added successfully", HttpStatus.OK);
    }

}
