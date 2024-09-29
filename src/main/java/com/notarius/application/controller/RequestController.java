package com.notarius.application.controller;

import com.notarius.application.exception.ApplicationException;
import com.notarius.application.model.entity.TinyUrl;
import com.notarius.application.service.TinyUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final TinyUrlService tinyUrlService;

    @GetMapping(value = "/")
    public ResponseEntity<String> getUrl(@RequestParam String url) {
        String result;

        try {
            TinyUrl tinyUrl = tinyUrlService.findUrl(url);

            if (tinyUrl == null) {
                return ResponseEntity.notFound().build();
            }

            result = tinyUrl.getSourceUrl();
        } catch (ApplicationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String url) {
        String result;

        try {
            result = tinyUrlService.shorten(url);
        } catch (ApplicationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }


}
