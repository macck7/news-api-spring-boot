package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.dao.NewsDao;
import com.navi.mynewsservice.entity.Article;
import com.navi.mynewsservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    NewsDao newsDao;

    public NewsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String addUserDetails(String email, String country, String category){
        return newsDao.addUserDetails(email,country,category);
    }

    @Override
    public List<Article> getNewsById(String id, String count) {
        return newsDao.getNewsById(id, count);
    }
}
