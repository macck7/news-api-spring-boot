package com.navi.mynewsservice.model;
import com.navi.mynewsservice.model.repo.SourceRepo;
import com.navi.mynewsservice.model.repo.UserRepo;
import com.navi.mynewsservice.model.schema.SourceDetails;
import com.navi.mynewsservice.model.schema.UserDetails;
import com.navi.mynewsservice.service.impl.AddUserService;
import com.navi.mynewsservice.service.impl.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.stream.Collectors;


@Repository
public class NewsDao {
//    private Set<String>userSet;
//    private Map<String, User> userDetails;
//
//    public NewsDao(){
//        userSet = new HashSet<>();
//        userDetails = new HashMap<>();
//    }

    @Autowired
    private FetchService fetchService;
    @Autowired
    private AddUserService addUserService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SourceRepo sourceRepo;



    public String addUserDetails(String email, String country, String category, List<String> sourceNames) {
        try {
            return addUserService.addUserDetails(email,country,category,sourceNames);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }




    public List<String> getNewsById(String id, String count, String from, String to) throws Exception {
        UserDetails user = userRepo.findByEmail(id);
        if (user == null) {
            // Handle user not found scenario
            return Collections.emptyList();
        }
        List<SourceDetails> sourceDetailsList = user.getSources();
        List<String> sourceNames = sourceDetailsList.stream()
                .map(SourceDetails::getName)
                .collect(Collectors.toList());

        return fetchService.getNewsById(user.getCountry(), user.getCategory(), count,sourceNames, from, to);
    }

    public List<String> getSources(String email) {
        UserDetails user = userRepo.findByEmail(email);
        if (user == null) {
            // Handle user not found scenario
            return Collections.emptyList();
        }

        return fetchService.getAllSources(user.getCountry(), user.getCategory());
    }


}
