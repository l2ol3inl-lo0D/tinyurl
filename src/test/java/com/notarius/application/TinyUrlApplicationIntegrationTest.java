package com.notarius.application;

import com.notarius.application.repository.TinyUrlRepository;
import com.notarius.application.utils.EntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.yml")
@ActiveProfiles("test")
public class TinyUrlApplicationIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    @Test
    void givenUrl_whenShortenUrl_thenReturn200WithBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/shorten")
                        .param("url", "https://www.google.com/")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN));

        assertTrue(tinyUrlRepository.findBySourceUrl("https://www.google.com/").isPresent());
    }

    @Test
    void givenUrl_whenFindUrl_thenReturn200WithBody() throws Exception {
        tinyUrlRepository.save(EntityFactory.createTinyUrl("https://www.superurl.com/", "test123"));

        mockMvc.perform(MockMvcRequestBuilders.get("/find")
                        .param("key", "test123")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void givenUrl_whenDoesNotExist_shouldReturn404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/find")
                        .param("key", "nourl")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInvalidUrl_whenShortenUrl_shouldReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/shorten")
                        .param("url", "invalidurl")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest()).andExpect(
                        content().string("Invalid Url: invalidurl"));
    }

    @Test
    void givenEmptyUrl_whenFindUrl_shouldReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/find")
                        .param("key", "")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Generated Url: "));
    }

}
