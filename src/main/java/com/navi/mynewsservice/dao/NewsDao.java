package com.navi.mynewsservice.dao;
import com.navi.mynewsservice.Contract.request.User;
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

    @Autowired
    private FetchService fetchService;
    @Autowired
    private AddUserService addUserService;


    public String addUserDetails(String email, String country, String category, List<String> sources) {
        try{
            if(addUserService.addUserDetails(email,country,category,sources)){
                userSet.add(email);
                userDetails.put(email,new User(email,country,category,sources));
                return "User added successfully";
            }
            else{
                return "User already exists";
            }
        }
        catch (Exception e){
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
