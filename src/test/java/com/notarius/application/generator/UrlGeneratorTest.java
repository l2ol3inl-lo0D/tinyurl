package com.notarius.application.generator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UrlGeneratorTest {

    @InjectMocks
    private UrlGenerator urlGenerator;

    @Test
    void givenValidUrl_whenGenerateUrl_shouldReturnGeneratedUrl() {
        String url = "https://www.google.com";
        String generatedUrl = urlGenerator.generateUrl(url);
        assertNotNull(generatedUrl);
    }

    @Test
    void givenEmptyUrl_whenGenerateUrl_shouldThrowIllegalArgumentException() {
        String url = "";
        try {
            urlGenerator.generateUrl(url);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Url cannot be null or empty", e.getMessage());
        }
    }

    @Test
    void giveNullUrl_whenGenerateUrl_shouldThrowIllegalArgumentException() {
        try {
            urlGenerator.generateUrl(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Url cannot be null or empty", e.getMessage());
        }
    }

    @Test
    void givenTwoValidUrls_whenGenerateUrl_shouldReturnDifferentHashes() {
        String url1 = "https://www.google.com";
        String url2 = "https://www.facebook.com";
        String hash1 = urlGenerator.generateUrl(url1);
        String hash2 = urlGenerator.generateUrl(url2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    void givenValidUrl_whenGeneratingUrl_shouldHave10Characters() {
        String url = "https://www.google.com";
        String generatedUrl = urlGenerator.generateUrl(url);
        assertEquals(10, generatedUrl.length());
    }

}
