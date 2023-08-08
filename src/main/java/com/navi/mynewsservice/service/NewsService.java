package com.navi.mynewsservice.service;

import java.util.List;

public interface NewsService {

    public List<String> getAllCountries();
    public List<String> getAllCategories();
    public void addCountry(String country);
    public void addCategory(String category);


    public String checkValidEmail(String email) ;


    public String showTopHeadlines(String country, String category);

    public String showCountOfHeadlines(String country, String category, Integer count);
}
