package com.navi.mynewsservice.service;

import com.navi.mynewsservice.entity.Article;

import java.util.List;

public interface NewsService {


    public String addUserDetails(String email, String country, String category);
    public List<Article> getNewsById(String country, String count);

}
