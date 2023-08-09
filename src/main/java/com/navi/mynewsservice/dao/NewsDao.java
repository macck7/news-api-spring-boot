package com.navi.mynewsservice.dao;

import com.navi.mynewsservice.entity.Article;
import com.navi.mynewsservice.entity.NewsResponse;
import com.navi.mynewsservice.entity.User;
import com.navi.mynewsservice.exception.CategoryNotFoundException;
import com.navi.mynewsservice.exception.InvalidEmailIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class NewsDao {
    private Set<String>userSet;
    private Map<String, User> userDetails;

    public NewsDao(){
        userSet = new HashSet<>();
        userDetails = new HashMap<>();
    }

    private Set<String> countries = new HashSet<>(Set.of("de", "hk", "tw", "pt", "lt", "lv", "ua", "hu", "ma", "id", "ie", "us", "eg", "il", "ae", "in", "za", "it", "mx", "my", "ve", "ar", "at", "au", "ng", "ro", "nl", "no", "rs", "be", "ru", "bg", "jp", "fr", "nz", "sa", "br", "se", "sg", "si", "sk", "gb", "ca", "ch", "kr", "cn", "gr", "co", "cu", "th", "cz", "ph", "pl", "tr"));
    private Set<String> categories = new HashSet<>(Set.of("business", "entertainment", "general", "health", "science", "sports", "technology"));
    public String addUserDetails(String email, String country, String category) {
        if(!checkValidEmail(email)) throw new InvalidEmailIdException("Invalid Email Id");
        if(countries.contains(country) == false) throw new CategoryNotFoundException("Country Not Found, Please write country code in small letters");
        if(categories.contains(category) == false) throw new CategoryNotFoundException("Category Not Found, please write correct category");

        User user = new User();
        user.setEmail(email);
        user.setSelectedCategory(category);
        user.setSelectedCountry(country);

        userSet.add(email);
        userDetails.put(email, user);

        return "User details saved successfully";

    }

    private boolean checkValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Autowired
    private RestTemplate restTemplate;

    public List<Article> getNewsById( String id, String count) {

        User user = userDetails.get(id);


        if (user != null) {
            String country = user.getSelectedCountry();
            String category = user.getSelectedCategory();
            String apiUrl = "https://newsapi.org/v2/top-headlines";
            String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";
            String url = apiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
            if(count != null) url = url + "&pageSize=" + Integer.parseInt(count);

            ResponseEntity<NewsResponse> newsResponseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, null, NewsResponse.class);

            NewsResponse newsResponse = newsResponseEntity.getBody();
            List<Article> articles = newsResponse.getArticles();

            if(articles.size() == 0) {
                throw new CategoryNotFoundException("No articles found for the given country and category.");
            }
            else return articles;
        }
        else return null;

    }



}
