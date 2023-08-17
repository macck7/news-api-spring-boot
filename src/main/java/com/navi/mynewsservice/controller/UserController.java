package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.model.repo.ApiCallRecordRepo;
import com.navi.mynewsservice.model.schema.ApiCallRecord;
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

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Long tt = 0L;
            apiCallRecordRepo.save(new ApiCallRecord("/add-user", "POST", e.getMessage(), tt));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/allcountry")
    public ResponseEntity<?> getAllcountries(){
        return new ResponseEntity<>(userService.getAllCountries(), HttpStatus.OK);
    }

    @GetMapping("/allcategory")
    public ResponseEntity<List<String>> getAllCategories(){
        return new ResponseEntity<>( userService.getAllCategories(),HttpStatus.OK);
    }

    @GetMapping("/addcountry")
    public ResponseEntity<?> addCountry(@RequestParam String country){
        userService.addCountry(country);
        return new ResponseEntity<>("Country added successfully",HttpStatus.OK);
    }

    @GetMapping("/addcategory")
    public ResponseEntity<String> addCategory(@RequestParam String category){
        userService.addCategory(category);
        return new ResponseEntity<>("Category added successfully",HttpStatus.OK);
    }
}
