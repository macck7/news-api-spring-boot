package com.navi.mynewsservice.service.impl;
import com.navi.mynewsservice.model.NewsDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    private NewsDao newsDao;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        newsService = new NewsServiceImpl(new RestTemplate());
        newsService.newsDao = newsDao;

    }


    @Test
    void testAddUserDetails() {
        String email = "user@example.com";
        String country = "us";
        String category = "general";
        List<String> sources = Collections.singletonList("source1");

        newsService.newsDao = newsDao;
        when(newsDao.addUserDetails(email, country, category, sources)).thenReturn("User details saved successfully");
        String result = newsService.addUserDetails(email, country, category, sources);
        assertEquals("User details saved successfully", result);
        verify(newsDao, times(1)).addUserDetails(email, country, category, sources);
    }

    @Test
    void testGetNewsById() throws Exception {
        String id = "user123";
        String count = "10";
        String from = "2023-07-18";
        String to = "2023-08-01";

        List<String> expectedNews = Arrays.asList("news1", "news2");
        when(newsDao.getNewsById(id, count, from, to)).thenReturn(expectedNews);
        List<String> result = newsService.getNewsById(id, count, from, to);
        assertEquals(expectedNews, result);
        verify(newsDao, times(1)).getNewsById(id, count, from, to);
    }

    @Test
    void testGetSources() {

        String id = "user123";
        List<String> expectedSources = Arrays.asList("source1", "source2");
        when(newsDao.getSources(id)).thenReturn(expectedSources);
        List<String> result = newsService.getSources(id);
        assertEquals(expectedSources, result);

    }


}