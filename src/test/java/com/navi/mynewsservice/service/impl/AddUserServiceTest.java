package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddUserServiceTest {


        @InjectMocks
        private AddUserService addUserService;

        @Mock
        private ValidateService validateService;

        @Mock
        private FetchService fetchService;

        @Test
        void testAddUserDetails() {
            String email = "user@example.com";
            String country = "us";
            String category = "technology";
            List<String> sources = Arrays.asList("source1", "source2");

            when(validateService.validateEmail(email)).thenReturn(true);
            when(validateService.validateCountry(country)).thenReturn(true);
            when(validateService.validateCategory(category)).thenReturn(true);

            when(fetchService.getAllSources(country, category)).thenReturn(Arrays.asList("source1", "source2", "source3"));
            boolean result = addUserService.addUserDetails(email, country, category, sources);
            assertTrue(result);

            verify(validateService, times(1)).validateEmail(email);
            verify(validateService, times(1)).validateCountry(country);
            verify(validateService, times(1)).validateCategory(category);
            verify(fetchService, times(1)).getAllSources(country, category);
        }

}