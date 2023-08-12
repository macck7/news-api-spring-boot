package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.model.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetAllCountries() {
        List<String> expectedCountries = Arrays.asList("Country1", "Country2");
        when(userDao.getAllData("country.csv")).thenReturn(expectedCountries);
        List<String> actualCountries = userService.getAllCountries();
        assertEquals(expectedCountries, actualCountries);
    }

    @Test
     void testGetAllCategories() {
        List<String> expectedCategories = Arrays.asList("Category1", "Category2");
        when(userDao.getAllData("category.csv")).thenReturn(expectedCategories);
        List<String> actualCategories = userService.getAllCategories();
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
     void testAddCountry() {
        String country = "NewCountry";
        userService.addCountry(country);
        verify(userDao).addData("country.csv", country);
    }

    @Test
     void testAddCategory() {
        String category = "NewCategory";
        userService.addCategory(category);
        verify(userDao).addData("category.csv", category);
    }

}