package com.navi.mynewsservice.controller;

import com.navi.mynewsservice.dao.User;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NewsController {
    @Autowired
    private NewsServiceImpl newsService;

    @GetMapping("/allcountry")
    public List<String> getAllcountries(){
        return newsService.getAllCountries();
    }



    @GetMapping("/allcategory")
    public List<String> getAllCategories(){
        return newsService.getAllCategories();
    }

    @GetMapping("/addcountry")
    public String addCountry(@RequestParam String country){
        newsService.addCountry(country);
        return "Country added successfully";
    }

    @GetMapping("/addcategory")
    public String addCategory(@RequestParam String category){
        newsService.addCategory(category);
        return "Category added successfully";
    }



    private final Map<Integer, User> userMap = new HashMap<>();
    int userId=1;

    @GetMapping("/{email}")
    public String getNews(@PathVariable String email){
        return newsService.checkValidEmail(email);
    }


    @GetMapping("/add-user")
    public String getUserDetails(
            @RequestParam String email,
            @RequestParam String country,
            @RequestParam String category) {

        User user = new User();
        user.setEmail(email);
        user.setSelectedCategory(category);
        user.setSelectedCountry(country);

        userMap.put(userId, user);
        System.out.println(user);
        userId++;
        System.out.println(userMap);
        return "User details saved successfully";

    }

    @GetMapping("/news/{id}")
    public String getNewsById(@PathVariable String id) {
        Integer userId = Integer.parseInt(id);
        User user = userMap.get(userId);

        if (user != null) {

            String country = user.getSelectedCountry();
            String category = user.getSelectedCategory();
            System.out.println(country);
            return newsService.showTopHeadlines(country, category);
        } else {
            return "User not found";
        }
    }


    @GetMapping("/news")
    public String showCountOfHeadlines(
            @RequestParam String id,
            @RequestParam String count) {
        Integer userId = Integer.parseInt(id);
        Integer maximum = Integer.parseInt(count);
        User user = userMap.get(userId);

        if (user != null) {

            String country = user.getSelectedCountry();
            String category = user.getSelectedCategory();
            System.out.println(country);
            return newsService.showCountOfHeadlines(country, category,maximum);
        } else {
            return "User not found";
        }
    }






}
