package com.navi.mynewsservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ValidateServiceTest {

    @InjectMocks
    private ValidateService validateService;

    @Test
    void testValidateCountry() {
        String country = "us";
        assertTrue(validateService.validateCountry(country));
    }

    @Test
    void testValidateCategory() {
        String category = "science";
        assertTrue(validateService.validateCategory(category));
    }

    @Test
    void testValidateDate() {
        String date = "2023-06-04";
        assertTrue(validateService.validateDate(date));
    }

    @Test
    void testValidateEmail() {
        String email = "abcd.@gmail.com";
        assertTrue(validateService.validateEmail(email));
    }
}