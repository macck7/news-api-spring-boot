package com.navi.mynewsservice.service.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.mynewsservice.dao.NewsDao;
import com.navi.mynewsservice.exception.CountryNotFoundException;
import com.navi.mynewsservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    public NewsDao newsDao;


    @Override
    public List<String> getAllCountries() {

        String path = "country.csv";
        return newsDao.getAllData(path);
    }

    @Override
    public List<String> getAllCategories() {
        String path = "category.csv";
        return newsDao.getAllData(path);
    }


    @Override
    public void addCountry(String country){
        String path = "country.csv";
        newsDao.addData(path,country);
    }
    @Override
    public void addCategory(String category){
        String file = "category.csv";
        newsDao.addData(file,category);
    }

    @Override
    public String checkValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()==true){
            return "Valid";
        }
        else{
            return "Invalid";
        }
    }


    @Autowired
    private RestTemplate restTemplate;

    public NewsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public String showTopHeadlines(String country, String category) {
        String apiUrl = "https://newsapi.org/v2/top-headlines";
        String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";
        String url = apiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);

            int articlesLength = jsonResponse.get("articles").size();
            if (articlesLength == 0) {
                throw new CountryNotFoundException( "No articles found for the given country and category.");
            }
            return response;
        }catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
        return "";
    }

    @Override
    public String showCountOfHeadlines(String country, String category, Integer count) {
        String apiUrl = "https://newsapi.org/v2/top-headlines";
        String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";
        String url = apiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;

        String response = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);

            JsonNode articlesArray = jsonResponse.get("articles");
            int numberOfArticles = articlesArray.size();

            int articlesToRetrieve = count;
            articlesToRetrieve = Math.min(articlesToRetrieve, numberOfArticles);
            String articles = "";
            for (int i = 0; i < articlesToRetrieve; i++) {
                JsonNode article = articlesArray.get(i);
                articles += article.get("title").asText() + "\n";
            }
            return articles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No article Found";
    }
}
