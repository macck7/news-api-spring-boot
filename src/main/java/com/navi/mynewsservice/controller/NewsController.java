package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class NewsController {
    @Autowired
    private NewsServiceImpl newsService;

    @PostMapping ("/add-user")
    public ResponseEntity<?> addUserDetails(@RequestBody User user) {
        try{
            return new ResponseEntity<>(
                    newsService.addUserDetails(
                            user.getEmail(),
                            user.getCountry(),
                            user.getCategory(),
                            user.getSources()), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable String id,
                                         @RequestParam(required = false) String count,
                                         @RequestParam(required = false) String from,
                                         @RequestParam(required = false) String to) {
        try{
            return new ResponseEntity<>(
                    newsService.getNewsById(id, count,from,to),
                    HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sources/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable String id) {
        try{
            return new ResponseEntity<>(newsService.getSources(id),
                    HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
