package com.notarius.application.controller;

import com.notarius.application.exception.ApplicationException;
import com.notarius.application.service.TinyUrlService;
import com.notarius.application.utils.EntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestControllerTest {

    @InjectMocks
    private RequestController requestController;

    @Mock
    private TinyUrlService tinyUrlService;

    @Test
    void givenValidUrl_whenShortenUrl_shouldReturnGeneratedUrl() {
        String url = "https://www.google.com";
        String generatedUrl = "test123";
        when(tinyUrlService.shorten(url)).thenReturn(generatedUrl);
        ResponseEntity<String> result = requestController.shortenUrl(url);
        assertEquals(generatedUrl, result.getBody());
    }

    @Test
    void givenInvalidUrl_whenShortenUrl_shouldReturn400() {
        String url = "invalidurl";
        when(tinyUrlService.shorten(url)).thenThrow(new ApplicationException("Invalid Url: " + url));
        ResponseEntity<String> result = requestController.shortenUrl(url);
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Invalid Url: " + url, result.getBody());
    }

    @Test
    void givenUrl_whenShortenUrl_andInternalError_shouldReturn500() {
        String url = "https://www.google.com";
        when(tinyUrlService.shorten(url)).thenThrow(new RuntimeException("Internal Error"));
        ResponseEntity<String> result = requestController.shortenUrl(url);
        assertEquals(500, result.getStatusCode().value());
        assertEquals("Internal Error", result.getBody());
    }

    @Test
    void givenGeneratedUrl_whenGetUrl_shouldReturnSourceUrl() {
        String url = "test123";
        String sourceUrl = "https://www.google.com";
        when(tinyUrlService.findUrl(url)).thenReturn(EntityFactory.createTinyUrl(sourceUrl, url));
        ResponseEntity<String> result = requestController.getUrl(url);
        assertEquals(sourceUrl, result.getBody());
    }

    @Test
    void givenInvalidUrl_whenGetUrl_shouldReturn400() {
        String url = "invalidurl";
        when(tinyUrlService.findUrl(url)).thenThrow(new ApplicationException("Invalid Url: " + url));
        ResponseEntity<String> result = requestController.getUrl(url);
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Invalid Url: " + url, result.getBody());
    }

    @Test
    void givenUrl_whenGetUrl_andInternalError_shouldReturn500() {
        String url = "test123";
        when(tinyUrlService.findUrl(url)).thenThrow(new RuntimeException("Internal Error"));
        ResponseEntity<String> result = requestController.getUrl(url);
        assertEquals(500, result.getStatusCode().value());
        assertEquals("Internal Error", result.getBody());
    }

    @Test
    void givenGeneratedUrl_whenGetUrlIsNotFound_shouldReturn404() {
        String url = "test123";
        when(tinyUrlService.findUrl(url)).thenReturn(null);
        ResponseEntity<String> result = requestController.getUrl(url);
        assertEquals(404, result.getStatusCode().value());
    }
}
