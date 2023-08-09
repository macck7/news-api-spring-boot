package com.navi.mynewsservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.dao.NewsDao;
import com.navi.mynewsservice.entity.Article;
import com.navi.mynewsservice.entity.NewsResponse;
import com.navi.mynewsservice.entity.Source;
import com.navi.mynewsservice.entity.Sources;
import com.navi.mynewsservice.exception.CategoryNotFoundException;
import com.navi.mynewsservice.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.InvalidMidiDataException;
import java.util.ArrayList;
import java.util.List;
@Service
public class FetchService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ValidateService validateService;

    public  List<String> getAllSources(String country, String category) {
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
            return articles;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> getNewsById( String country,String category, String count, List<String> sources, String from, String to) throws Exception {
        String apiUrl = "https://newsapi.org/v2/top-headlines";
        String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";
        String url = apiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;

        if(from != null){
            if(validateService.validateDate(from)) {
                throw new InvalidDataException("News can't be shown before 30 days");
            }
            else url = url + "&from=" + from;
        }

        if(to != null)  url = url + "&to=" + to;
        if(count != null) url = url + "&pageSize=" + Integer.parseInt(count);

        List<String> titles = new ArrayList<>();
        for(String source: sources) {

            ResponseEntity<NewsResponse> newsResponseEntity = restTemplate.exchange(
                    url+"&source=" + source, HttpMethod.GET, null, NewsResponse.class);
            NewsResponse newsResponse = newsResponseEntity.getBody();
            List<Article> articles = newsResponse.getArticles();
            for(Article article: articles) {
                titles.add(article.getTitle());
            }
        }

        if(titles.size() == 0) {
            throw new CategoryNotFoundException("No articles found for the given country and category.");
        }
        else return titles;
    }


}


