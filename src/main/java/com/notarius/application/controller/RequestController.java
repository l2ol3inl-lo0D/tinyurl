package com.notarius.application.controller;

import com.notarius.application.exception.ApplicationException;
import com.notarius.application.model.entity.TinyUrl;
import com.notarius.application.service.TinyUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RequestController {

    private final TinyUrlService tinyUrlService;

    @GetMapping(value = "/find")
    public ResponseEntity<String> getUrl(@RequestParam String key) {
        String result;

        try {
            TinyUrl tinyUrl = tinyUrlService.findUrl(key);

            if (tinyUrl == null) {
                log.error("Url not found: " + key);
                return ResponseEntity.notFound().build();
            }

            result = tinyUrl.getSourceUrl();
            log.info("Source url: " + result);
        } catch (ApplicationException e) {
            log.error("Bad request: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String url) {
        String result;

        try {
            result = tinyUrlService.shorten(url);
            log.info("Shortened url: " + result);
        } catch (ApplicationException e) {
            log.error("Bad request: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }


}
