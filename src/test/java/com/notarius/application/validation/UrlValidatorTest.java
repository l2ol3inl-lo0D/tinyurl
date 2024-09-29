package com.notarius.application.validation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UrlValidatorTest {

    @InjectMocks
    private UrlValidator urlValidator;

    @ParameterizedTest
    @ValueSource(strings = {"www.google.com", "http://www.google.com", "https://www.google.com", "https://www.google" +
            ".com/witharg&requestparam=test", "https://www.google.com/witharg?requestparam=test", "https://www.google" +
            ".com/test-hyphen"})
    void givenValidUrls_whenValidate_shouldReturnTrue(String url) {
        assertTrue(urlValidator.validate(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"www.\\\\  .com/", "http://www.\0\0\0\0\0''''''.com/", "https://www.thisistest*****" +
            ".com/************", "https://www.google.com/test!!!", "", " "})
    void givenInvalidUrls_whenValidate_shouldReturnFalse(String url) {
        assertFalse(urlValidator.validate(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test123", "54545454te"})
    void givenValidUrls_whenValidateGeneratedUrl_shouldReturnTrue(String url) {
        assertTrue(urlValidator.validateGeneratedUrl(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"' | delete all", "'{ drop.all }", "\"; DELETE * FROM TABLE;", "", " ",
            "thereistoomuchcharactershere"})
    void givenInvalidShortenUrls_whenValidateGeneratedUrl_shouldReturnFalse(String url) {
        assertFalse(urlValidator.validateGeneratedUrl(url));
    }

}
