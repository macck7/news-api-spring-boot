package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.model.UserDao;
import com.navi.mynewsservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    public UserDao userDao;

    @Override
    public List<String> getAllCountries() {
        return userDao.getAllData("country.csv");
    }

    @Override
    public List<String> getAllCategories() {
        return userDao.getAllData("category.csv");
    }

    @Override
    public void addCountry(String country){
        userDao.addData("country.csv",country);
    }
    @Override
    public void addCategory(String category){
        userDao.addData("category.csv",category);
    }
}
