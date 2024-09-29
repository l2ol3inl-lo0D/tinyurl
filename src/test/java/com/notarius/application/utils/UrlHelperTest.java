package com.notarius.application.utils;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UrlHelperTest {

    @ParameterizedTest
    @ValueSource(strings = {"www.google.com", "http://www.google.com", "https://www.google.com", "https://www.google" +
            ".com/witharg&requestparam=test", "https://www.google.com/witharg?requestparam=test", "https://www.google" +
            ".com/test-hyphen"})
    void givenValidUrls_whenCheckIsUrlValid_shouldReturnTrue(String url) {
        assertTrue(UrlHelper.isUrlValid(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"www.\\\\  .com/", "http://www.\0\0\0\0\0''''''.com/", "https://www.thisistest*****" +
            ".com/************", "https://www.google.com/test!!!"})
    void givenInvalidUrls_whenCheckIsUrlValid_shouldReturnFalse(String url) {
        assertFalse(UrlHelper.isUrlValid(url));
    }

    @ParameterizedTest
    @CsvSource({"http://www.GOOGLE.com, http://www.google.com",
            "www.google.com:443, https://www.google.com:443",
            "www.google.com:80, http://www.google.com:80",
            "google.com, http://www.google.com"})
    void givenUrls_whenNormalizeUrl_shouldReturnNormalizedUrl(String source, String expected) {
        String normalizedUrl = UrlHelper.normalizeUrl(source);
        assertEquals(expected, normalizedUrl);
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://www.google.com", "https://www.google.com"})
    void givenValidUrls_whenContainsProtocol_shouldReturnTrue(String url) {
        assertTrue(UrlHelper.containsProtocol(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"www.google.com", "://www.google.com"})
    void givenValidUrls_withoutProtocol_whenContainsProtocol_shouldReturnFalse(String url) {
        assertFalse(UrlHelper.containsProtocol(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test123", "weqwe12313", "valid44444444", "thisisatest"})
    void givenValidShortenUrls_whenIsGeneratedUrlValid_shouldReturnTrue(String url) {
        assertTrue(UrlHelper.isGeneratedUrlValid(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"' | delete all", "'{ drop.all }", "\"; DELETE * FROM TABLE;"})
    void givenInvalidShortenUrls_whenIsGeneratedUrlValid_shouldReturnFalse(String url) {
        assertFalse(UrlHelper.isGeneratedUrlValid(url));
    }

}
