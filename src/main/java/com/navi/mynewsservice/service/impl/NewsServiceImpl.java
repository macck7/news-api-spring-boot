package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.model.NewsDao;
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
    public NewsDao newsDao;

    public NewsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String addUserDetails(String email, String country, String category, List<String> sources){
        return newsDao.addUserDetails(email,country,category,sources);
    }

    @Override
    public List<String> getNewsById(String id, String count,String from, String to) throws Exception {
        return newsDao.getNewsById(id, count,from,to);
    }

    @Override
    public List<String> getSources(String id) {
        return newsDao.getSources(id);
    }

}
