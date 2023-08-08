import com.navi.mynewsservice.dao.NewsDao;
import com.navi.mynewsservice.service.impl.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class NewsServiceImplTest {

    @Mock
    private NewsDao newsDao;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllCountries() {
        List<String> countries = Arrays.asList("Country1", "Country2");
        when(newsDao.getAllData(anyString())).thenReturn(countries);
        List<String> result = newsService.getAllCountries();

        assertEquals(countries, result);
    }

    @Test
    public void testGetAllCategories() {
        List<String> categories = Arrays.asList("Category1", "Category2");
        when(newsDao.getAllData(anyString())).thenReturn(categories);

        List<String> result = newsService.getAllCategories();

        assertEquals(categories, result);
    }

    @Test
    void testAddCountry() {
        String country = "NewCountry";
        doNothing().when(newsDao).addData(anyString(), anyString());
        newsService.addCountry(country);

        verify(newsDao).addData(anyString(), eq(country));

    }


    @Test
    void testAddCategory() {
        String categroy = "Category";
        doNothing().when(newsDao).addData(anyString(), anyString());
        newsService.addCategory(categroy);
        verify(newsDao).addData(anyString(), eq(categroy));
    }


    @Test
    public void testCheckValidEmailValid() {
        String email = "user@example.com";
        String result = newsService.checkValidEmail(email);

        assertEquals("Valid", result);
    }

    @Test
    void testShowTopHeadlines() {
        String country = "us";
        String category = "technology";
        String expectedApiResponse = "Some response";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(expectedApiResponse);

        String actualResponse = newsService.showTopHeadlines(country, category);
        verify(restTemplate).getForObject(
                eq("https://newsapi.org/v2/top-headlines" +
                        "?country=" + country +
                        "&category=" + category +
                        "&apiKey=b46085f75a39489b88704fb9c9f7e4fc"),
                eq(String.class)
        );

        assertEquals(expectedApiResponse, actualResponse);
    }



}
