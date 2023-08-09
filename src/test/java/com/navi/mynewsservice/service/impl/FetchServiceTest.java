package com.navi.mynewsservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchServiceTest {

    @Mock
    private FetchService fetchService;

    @Mock
    private ValidateService validateService;

    @InjectMocks
    private AddUserService addUserService;

    @Test
    void testGetAllSources() {
        String country = "us";
        String category = "technology";
        when(validateService.validateCountry(country)).thenReturn(true);
        when(validateService.validateCategory(category)).thenReturn(true);
        when(fetchService.getAllSources(country, category)).thenReturn(Arrays.asList("source1", "source2", "source3"));
        verify(
    }

    @Test
    void testGetNewsById() {
    }
}