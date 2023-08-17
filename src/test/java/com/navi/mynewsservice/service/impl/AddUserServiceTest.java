package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.model.repo.SourceRepo;
import com.navi.mynewsservice.model.repo.UserRepo;
import com.navi.mynewsservice.model.schema.SourceDetails;
import com.navi.mynewsservice.model.schema.UserDetails;
import com.navi.mynewsservice.service.UserService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddUserServiceTest {

        @InjectMocks
        private AddUserService addUserService;

        @Mock
        private ValidateService validateService;

        @Mock
        private FetchService fetchService;

        @Mock
        private UserRepo userRepo;

        @Mock
        private SourceRepo sourceRepo;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testAddUserDetails() {
            String email = "abc@example.com";
            String country = "us";
            String category = "science";
            List<String> sourceNames = Arrays.asList("source1", "source2");

            // Mocking validation services
            when(validateService.validateEmail(email)).thenReturn(true);
            when(validateService.validateCountry(country)).thenReturn(true);
            when(validateService.validateCategory(category)).thenReturn(true);

            // Mocking fetchService
            List<String> sourcesFromUrl = Arrays.asList("source1", "source2", "source3");
            when(fetchService.getAllSources(country, category)).thenReturn(sourcesFromUrl);

            // Mocking userRepo
            when(userRepo.findByEmail(email)).thenReturn(null);

            // Mocking sourceRepo
            when(sourceRepo.findByName(anyString())).thenReturn(null);

            // Test the method
            String result = addUserService.addUserDetails(email, country, category, sourceNames);
            assertEquals("User details added", result);

            // Verify interactions
            verify(validateService).validateEmail(email);
            verify(validateService).validateCountry(country);
            verify(validateService).validateCategory(category);
            verify(fetchService).getAllSources(country, category);
            verify(userRepo).findByEmail(email);
            verify(sourceRepo, times(2)).findByName(anyString());
            verify(sourceRepo, times(2)).save(any(SourceDetails.class));
            verify(userRepo).save(any(UserDetails.class));
        }

        // Add more test methods...

    }
