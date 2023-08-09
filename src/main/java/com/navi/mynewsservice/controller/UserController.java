package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import com.navi.mynewsservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl newsService;

    @GetMapping("/allcountry")
    public ResponseEntity<?> getAllcountries(){
        return new ResponseEntity<>(newsService.getAllCountries(), HttpStatus.OK);
    }



    @GetMapping("/allcategory")
    public ResponseEntity<List<String>> getAllCategories(){
        return new ResponseEntity<>( newsService.getAllCategories(),HttpStatus.OK);
    }

    @GetMapping("/addcountry")
    public ResponseEntity<?> addCountry(@RequestParam String country){
        newsService.addCountry(country);
        return new ResponseEntity<>("Country added successfully",HttpStatus.OK);
    }

    @GetMapping("/addcategory")
    public ResponseEntity<String> addCategory(@RequestParam String category){
        newsService.addCategory(category);
        return new ResponseEntity<>("Category added successfully",HttpStatus.OK);
    }
}
