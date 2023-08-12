package com.navi.mynewsservice.service;

import com.navi.mynewsservice.entity.Article;

import java.util.List;

public interface NewsService {


    public String addUserDetails(String email, String country, String category, List<String> sources);

    public List<String> getNewsById(String country,String count,String from, String to) throws Exception;

    public List<String> getSources(String id);
}
