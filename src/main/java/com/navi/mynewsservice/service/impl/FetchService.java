package com.navi.mynewsservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.mynewsservice.entity.Article;
import com.navi.mynewsservice.entity.NewsResponse;
import com.navi.mynewsservice.entity.Source;
import com.navi.mynewsservice.entity.Sources;
import com.navi.mynewsservice.exception.CategoryNotFoundException;
import com.navi.mynewsservice.exception.InvalidDateException;
import com.navi.mynewsservice.model.schema.ApiCallRecord;
import com.navi.mynewsservice.model.repo.ApiCallRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class FetchService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ValidateService validateService;
    @Autowired
    private ApiCallRecordRepo apiCallRecordRepo;

    public  List<String> getAllSources(String country, String category) {

        long startTime = System.currentTimeMillis();
        String apiUrl = "https://newsapi.org/v2/top-headlines/sources";
        String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";
        String url = apiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);


        ResponseEntity<Sources> sourcesResponseEntity = restTemplate.exchange(
                url, HttpMethod.GET, null, Sources.class);

        Sources source = sourcesResponseEntity.getBody();
        List<Source> sourceList = source.getSources();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            JsonNode articlesArray = jsonResponse.get("sources");
            int numberOfArticles = articlesArray.size();

            List<String> articles = new ArrayList<>();
            for (int i = 0; i < numberOfArticles; i++) {
                JsonNode article = articlesArray.get(i);
                articles.add(article.get("id").asText());
            }

            long endTime = System.currentTimeMillis();
            long tt = endTime - startTime;
            apiCallRecordRepo.save(new ApiCallRecord("/sources", "GET ", "response.toString()", tt));
            return articles;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> getNewsById(String country, String category, String count, List<String> sources, String from, String to) throws Exception {
        long startTime = System.currentTimeMillis();
        String apiUrl = "https://newsapi.org/v2/top-headlines";
        String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";

        StringBuilder urlBuilder = new StringBuilder(apiUrl)
                .append("?country=").append(country)
                .append("&category=").append(category)
                .append("&apiKey=").append(apiKey);

        String endpoint = "/top-headlines";
        if (from != null) {
            if (validateService.validateDate(from)) {
                throw new InvalidDateException("News can't be shown before 30 days");
            } else {
                urlBuilder.append("&from=").append(from);
                endpoint = endpoint + "/from";
            }
        }

        if (to != null) {
            urlBuilder.append("&to=").append(to);
            endpoint = endpoint + "/to";
        }

        if (count != null) {
            urlBuilder.append("&pageSize=").append(Integer.parseInt(count));
            endpoint = endpoint + "/count";
        }

        List<String> titles = new ArrayList<>();
        int i = 1;
        for (String source : sources) {
            String sourceUrl = urlBuilder.toString() + "&source=" + source;
            ResponseEntity<NewsResponse> newsResponseEntity = restTemplate.exchange(
                    sourceUrl, HttpMethod.GET, null, NewsResponse.class);
            NewsResponse newsResponse = newsResponseEntity.getBody();
            List<Article> articles = newsResponse.getArticles();
            for (Article article : articles) {
                titles.add(article.getTitle());
            }
            if (i == 1) {
                endpoint = endpoint + "/source";
                i = 0;
            }
        }

        if (titles.isEmpty()) {
            throw new CategoryNotFoundException("No articles found for the given country and category.");
        }

        long endTime = System.currentTimeMillis();
        long tt = endTime - startTime;
        apiCallRecordRepo.save(new ApiCallRecord(endpoint, "GET ", "titles.toString()", tt));
        return titles;
    }



}



