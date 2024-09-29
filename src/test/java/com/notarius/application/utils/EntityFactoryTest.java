package com.notarius.application.utils;

import com.notarius.application.model.entity.TinyUrl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityFactoryTest {

    @Test
    void givenSourceUrlAndGeneratedUrl_whenCreateTinyUrl_shouldReturnTinyUrl() {
        TinyUrl tinyUrl = EntityFactory.createTinyUrl("http://www.google.com", "test123");
        assertEquals("http://www.google.com", tinyUrl.getSourceUrl());
        assertEquals("test123", tinyUrl.getGeneratedUrl());
    }
}
