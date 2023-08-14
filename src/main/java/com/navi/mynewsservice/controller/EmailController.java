package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    EmailService emailService;

    @PostMapping("/send-email/{id}")
    public ResponseEntity<?> sendEmail(@PathVariable String id) {
    try{
        return new ResponseEntity<>(emailService.getHeadlines(id), HttpStatus.OK);
    }catch (Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    }

    @GetMapping("/top-user")
    public ResponseEntity<?> topUserWithMostApiRequest(){
        try {
            return new ResponseEntity<>(emailService.topUserWithMostApiRequest(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/subscribe/{email}")
    public String isSubscribed(@PathVariable String email ){
        try {
            String response =  emailService.addToScriberList(email);
            return response;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @DeleteMapping("/unsubscribe/{email}")
    public String unsubscribe(@PathVariable String email){
        try {
            return emailService.removeToScriberList(email);
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
