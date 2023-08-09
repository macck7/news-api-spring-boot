package com.navi.mynewsservice.service.impl;
import com.navi.mynewsservice.dao.NewsDao;
import com.navi.mynewsservice.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NewsServiceImplTest {

    @Mock
    private NewsDao newsDao;

//    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        newsService = new NewsServiceImpl(new RestTemplate());
        newsService.newsDao = newsDao;
    }


    @Test
    void testAddUserDetails() throws Exception {
        String email = "test@example.com";
        String country = "us";
        String category = "technology";
        String expectedResult = "User details saved successfully"; // Change this according to your implementation
        when(newsDao.addUserDetails(email, country, category)).thenReturn(expectedResult);
        String result = newsService.addUserDetails(email, country, category);
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetNewsById() throws Exception{
        String id = "user123";
        String count = "10";
        List<Article> expectedArticles = List.of(new Article(), new Article());
        when(newsDao.getNewsById(id, count)).thenReturn(expectedArticles);
        List<Article> result = newsService.getNewsById(id, count);
        assertEquals(expectedArticles, result);
    }
}