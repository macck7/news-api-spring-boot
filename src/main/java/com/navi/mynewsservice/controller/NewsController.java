package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.entity.Article;
import com.navi.mynewsservice.entity.User;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class NewsController {
    @Autowired
    private NewsServiceImpl newsService;

    @PostMapping ("/add-user")
    public ResponseEntity<?> addUserDetails(@RequestBody User user) {
        try{
            return new ResponseEntity<>(newsService.addUserDetails(user.getEmail(),
                    user.getSelectedCountry(), user.getSelectedCategory()), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable String id, @RequestParam(required = false) String count) {
        try{
            return new ResponseEntity<>(newsService.getNewsById(id, count),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
