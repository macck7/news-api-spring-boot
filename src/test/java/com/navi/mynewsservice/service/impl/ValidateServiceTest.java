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
    void testValidateCountry_ValidCountry() {
        String country = "us";
        assertTrue(validateService.validateCountry(country));
    }

    @Test
    void testValidateCountry_InvalidCountry() {
        String country = "invalid_country";
        assertFalse(validateService.validateCountry(country));
    }

    @Test
    void testValidateCategory_ValidCategory() {
        String category = "science";
        assertTrue(validateService.validateCategory(category));
    }

    @Test
    void testValidateCategory_InvalidCategory() {
        String category = "invalid_category";
        assertFalse(validateService.validateCategory(category));
    }

    @Test
    void testValidateDate_ValidDate() {
        String date = "2023-06-04";
        assertTrue(validateService.validateDate(date));
    }


    @Test
    void testValidateEmail_ValidEmail() {
        String email = "abcd@gmail.com";
        assertTrue(validateService.validateEmail(email));
    }

    @Test
    void testValidateEmail_InvalidEmail() {
        String email = "invalid_email";
        assertFalse(validateService.validateEmail(email));
    }
}
