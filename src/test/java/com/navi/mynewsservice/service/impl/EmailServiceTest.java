package com.navi.mynewsservice.service.impl;

import com.navi.mynewsservice.Contract.request.User;
import com.navi.mynewsservice.model.repo.RequestCountRepository;
import com.navi.mynewsservice.model.repo.SubscriberRepository;
import com.navi.mynewsservice.model.schema.RequestCount;
import com.navi.mynewsservice.model.schema.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private FetchService fetchService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private RequestCountRepository requestCountRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHeadlines() {
        // Mocking
        String userEmail = "user@example.com";
        List<String> headlines = Arrays.asList("Headline 1", "Headline 2");

        when(fetchService.fetchNews(userEmail)).thenReturn(headlines);
        when(requestCountRepository.findByEmail(userEmail)).thenReturn(null);

        // Test
        String result = emailService.getHeadlines(userEmail);

        // Assertion
        assertEquals("Email sent successfully", result);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(requestCountRepository, times(1)).save(any(RequestCount.class));
    }

    @Test
    void testSendWelcomeMail() {
        // Test setup
        List<String> roles = new ArrayList<>(); // Assuming you have a list of roles
        User user = new User("John", "Doe", "user@example.com", roles);

        // Test
        String result = emailService.sendWelcomeMail(user);

        // Assertion
        assertEquals("Email sent successfully!", result);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }


    @Test
    void testTopUserWithMostApiRequest() {
        // Mocking
        Object[] user1 = {"user1@example.com", 10};
        Object[] user2 = {"user2@example.com", 8};

        List<Object[]> topUsersWithCounts = Arrays.asList(user1, user2);

        when(requestCountRepository.findTopUsersWithCounts()).thenReturn(topUsersWithCounts);

        // Test
        List<String> result = emailService.topUserWithMostApiRequest();

        // Assertion
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("user1@example.com (10 requests)"));
        assertTrue(result.contains("user2@example.com (8 requests)"));
    }

    @Test
    void testAddToSubscriberList() {
        // Test setup
        String email = "user@example.com";

        when(subscriberRepository.findByEmail(email)).thenReturn(null);

        // Test
        String result = emailService.addToScriberList(email);

        // Assertion
        assertEquals("Subscriber added successfully.", result);
        verify(subscriberRepository, times(1)).save(any());
    }

    @Test
    void testRemoveFromSubscriberList() {
        // Test setup
        String email = "user@example.com";
        Subscriber existingSubscriber = new Subscriber();
        existingSubscriber.setEmail(email);

        when(subscriberRepository.findByEmail(email)).thenReturn(existingSubscriber);

        // Test
        String result = emailService.removeToScriberList(email);

        // Assertion
        assertEquals("Subscriber removed successfully.", result);
        verify(subscriberRepository, times(1)).delete(existingSubscriber);
    }

    // Write more tests for other methods as needed
}
