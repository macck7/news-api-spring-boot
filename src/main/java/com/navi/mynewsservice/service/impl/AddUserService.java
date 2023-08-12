package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.exception.CategoryNotFoundException;
import com.navi.mynewsservice.exception.IncorrectSourcesException;
import com.navi.mynewsservice.exception.InvalidEmailIdException;
import com.navi.mynewsservice.model.repo.SourceRepo;
import com.navi.mynewsservice.model.repo.UserRepo;
import com.navi.mynewsservice.model.schema.SourceDetails;
import com.navi.mynewsservice.model.schema.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

        @Autowired
        private UserRepo userRepo;
        @Autowired
        private SourceRepo sourceRepo;



public String addUserDetails(String email,
                              String country,
                              String category,
                              List<String> sourceNames ){

                if(!validateService.validateEmail(email)) throw new
                        InvalidEmailIdException("Invalid Email Id");
                if(!validateService.validateCountry(country)) throw new
                        CategoryNotFoundException("Country Not Found, " +
                        "Please write country code in small letters");
                if(!validateService.validateCategory(category)) throw new
                        CategoryNotFoundException("Category Not Found, " +
                        "please write correct category");

                List<String> sourcesFromUrl = fetchService.getAllSources(country,category);
                for(String source: sourceNames){
                    if(sourcesFromUrl!=null && !sourcesFromUrl.contains(source)) {
                        throw new IncorrectSourcesException("Soruces are not valid");
                    }
                }

                String response;
                if(userRepo.findByEmail(email) != null) response = "User details updated";
                else response = "User details added";

                List<SourceDetails> sources = new ArrayList<>();
                for (String sourceName : sourceNames) {
                        SourceDetails existingSource = sourceRepo.findByName(sourceName);
                        if (existingSource == null) {
                                existingSource = new SourceDetails(sourceName);
                                sourceRepo.save(existingSource);
                        }
                        sources.add(existingSource);
                }
                UserDetails userDetails = new UserDetails(email, country, category, sources);
                userRepo.save(userDetails);
                return response;
        }
}
