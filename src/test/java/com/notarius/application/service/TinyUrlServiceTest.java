package com.notarius.application.service;

import com.notarius.application.exception.ApplicationException;
import com.notarius.application.generator.UrlGenerator;
import com.notarius.application.model.entity.TinyUrl;
import com.notarius.application.repository.TinyUrlRepository;
import com.notarius.application.utils.EntityFactory;
import com.notarius.application.validation.UrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TinyUrlServiceTest {

    @InjectMocks
    private TinyUrlService tinyUrlService;

    @Mock
    private TinyUrlRepository tinyUrlRepository;

    @Mock
    private UrlValidator urlValidator;

    @Mock
    private UrlGenerator urlGenerator;

    @Test
    public void givenValidUrl_whenShortenUrl_shouldReturnGeneratedUrl() {
        String url = "https://www.google.com";

        when(tinyUrlRepository.findBySourceUrl(url)).thenReturn(Optional.of(EntityFactory.createTinyUrl(url,
                "test123")));
        when(urlValidator.validate(url)).thenReturn(true);
        String generatedUrl = tinyUrlService.shorten(url);
        assertEquals("test123", generatedUrl);
    }

    @Test
    public void givenInvalidUrl_whenShortenUrl_shouldThrowException() {
        String url = "invalidurl";
        when(urlValidator.validate(url)).thenReturn(false);
        try {
            tinyUrlService.shorten(url);
            fail("Should throw application exception");
        } catch (ApplicationException e) {
            assertEquals("Invalid Url: invalidurl", e.getMessage());
        }
    }

    @Test
    public void givenValidUrl_whenFindUrl_shouldReturnTinyUrl() {
        String url = "test123";
        when(tinyUrlRepository.findByGeneratedUrl(url)).thenReturn(Optional.of(
                EntityFactory.createTinyUrl("https://www.google.com", url)));
        when(urlValidator.validateGeneratedUrl(url)).thenReturn(true);
        TinyUrl tinyUrl = tinyUrlService.findUrl(url);
        assertEquals("https://www.google.com", tinyUrl.getSourceUrl());
    }
}
