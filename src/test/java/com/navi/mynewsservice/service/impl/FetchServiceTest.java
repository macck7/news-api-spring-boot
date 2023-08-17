package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.entity.Sources;
import com.navi.mynewsservice.exception.InvalidDateException;
import com.navi.mynewsservice.model.repo.ApiCallRecordRepo;
import com.navi.mynewsservice.model.repo.NewsDataRepository;
import com.navi.mynewsservice.model.repo.UserRepo;
import com.navi.mynewsservice.model.schema.News;
import com.navi.mynewsservice.model.schema.NewsData;
import com.navi.mynewsservice.model.schema.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FetchServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ValidateService validateService;

    @Mock
    private ApiCallRecordRepo apiCallRecordRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private NewsDataRepository newsDataRepository;

    @InjectMocks
    private FetchService fetchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSources() {
        // Mocking
        String country = "us";
        String category = "technology";
        String apiKey = "b46085f75a39489b88704fb9c9f7e4fc";
        String apiUrl = "https://newsapi.org/v2/top-headlines/sources";
        String url = apiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;

        String jsonResponse = "{\"sources\": [{\"id\": \"source1\"}, {\"id\": \"source2\"}]}";
        ResponseEntity<Sources> sourcesResponseEntity = new ResponseEntity<>(new Sources(), HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.GET, null, Sources.class)).thenReturn(sourcesResponseEntity);
        when(restTemplate.getForObject(url, String.class)).thenReturn(jsonResponse);

        // Test
        List<String> sources = fetchService.getAllSources(country, category);

        // Assertion
        assertNotNull(sources);
        assertEquals(2, sources.size());
        assertTrue(sources.contains("source1"));
        assertTrue(sources.contains("source2"));
    }


    @Test
    void testGetNewsById_InvalidDate() {
        // Test setup
        String country = "us";
        String category = "technology";
        String count = "5";
        List<String> sources = Arrays.asList("source1", "source2");
        String from = "invalid_date";

        when(validateService.validateDate(from)).thenReturn(true);

        // Test and assertion for InvalidDateException
        assertThrows(InvalidDateException.class,
                () -> fetchService.getNewsById(country, category, count, sources, from, null));
    }



    @Test
    void testFetchNews() {

        String userEmail = "user@example.com";
        UserDetails userDetails = new UserDetails();
        userDetails.setCountry("us");
        userDetails.setCategory("technology");

        List<News> newsList = new ArrayList<>();
        newsList.add(new News("News Title 1"));
        newsList.add(new News("News Title 2"));

        NewsData newsData = new NewsData();
        newsData.setCountry(userDetails.getCountry());
        newsData.setCategory(userDetails.getCategory());
        newsData.setNewsList(newsList);

        when(userRepo.findByEmail(userEmail)).thenReturn(userDetails);
        when(newsDataRepository.findByCountryAndCategory(userDetails.getCountry(), userDetails.getCategory())).thenReturn(newsData);

        List<String> titles = fetchService.fetchNews(userEmail);

        assertNotNull(titles);
        assertEquals(2, titles.size());
        assertTrue(titles.contains("News Title 1"));
        assertTrue(titles.contains("News Title 2"));
    }


}
