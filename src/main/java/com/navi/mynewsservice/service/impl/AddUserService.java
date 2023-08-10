package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.exception.CategoryNotFoundException;
import com.navi.mynewsservice.exception.IncorrectSourcesException;
import com.navi.mynewsservice.exception.InvalidEmailIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AddUserService {

        @Autowired
        private RestTemplate restTemplate;
        @Autowired
        private FetchService fetchService;
        @Autowired
        private ValidateService validateService;




public boolean addUserDetails(String email,
                              String country,
                              String category,
                              List<String> sources ){

        if(!validateService.validateEmail(email)) throw new
                InvalidEmailIdException("Invalid Email Id");
        if(!validateService.validateCountry(country)) throw new
                CategoryNotFoundException("Country Not Found, " +
                "Please write country code in small letters");
        if(!validateService.validateCategory(category)) throw new
                CategoryNotFoundException("Category Not Found, " +
                "please write correct category");


        List<String> sourcesFromUrl = fetchService.getAllSources(country,category);
        for(String source: sources){
            if(!sourcesFromUrl.contains(source)) {
                throw new IncorrectSourcesException("Soruces are not valid");
            }
        }
        return true;

}


}
