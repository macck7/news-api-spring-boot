package com.navi.mynewsservice.dao;
import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.dao.repo.SourceRepo;
import com.navi.mynewsservice.dao.repo.UserRepo;
import com.navi.mynewsservice.dao.schema.SourceDetails;
import com.navi.mynewsservice.dao.schema.UserDetails;
import com.navi.mynewsservice.entity.Article;
import com.navi.mynewsservice.service.impl.AddUserService;
import com.navi.mynewsservice.service.impl.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.*;


@Repository
public class NewsDao {
    private Set<String>userSet;
    private Map<String, User> userDetails;

    public NewsDao(){
        userSet = new HashSet<>();
        userDetails = new HashMap<>();
    }

//    @Autowired
//    private FetchService fetchService;
//    @Autowired
//    private AddUserService addUserService;


    private

    @Autowired
    UserRepo userRepo;

    @Autowired
    SourceRepo sourceRepo;


    public String addUserDetails(String email, String country, String category, List<String> sourceNames) {
        try {
            UserDetails existingUser = userRepo.findByEmail(email);

            if (existingUser != null) {
                // User already exists, update their details
                existingUser.setCountry(country);
                existingUser.setCategory(category);

                // Update the user's sources
                List<SourceDetails> sources = new ArrayList<>();
                for (String sourceName : sourceNames) {
                    SourceDetails existingSource = sourceRepo.findByName(sourceName);
                    if (existingSource == null) {
                        existingSource = new SourceDetails(sourceName);
                        sourceRepo.save(existingSource);
                    }
                    sources.add(existingSource);
                }
                existingUser.setSources(sources);

                userRepo.save(existingUser);

                return "User details updated";
            } else {
                // User doesn't exist, create a new user
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

                return "User added successfully";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }





    public List<String> getNewsById(String id, String count,String from, String to) throws Exception {
        User user = userDetails.get(id);
        return fetchService.getNewsById(user.getCountry(),user.getCategory(),count,user.getSources(),from,to);
    }

    public List<String> getSources(String id) {
        User user = userDetails.get(id);
        return fetchService.getAllSources(
                user.getCountry(),
                user.getCategory());

    }



}
