package com.navi.mynewsservice.service;

import java.util.List;

public interface UserService {
    public List<String> getAllCountries();
    public List<String> getAllCategories();
    public void addCountry(String country);
    public void addCategory(String category);
}
