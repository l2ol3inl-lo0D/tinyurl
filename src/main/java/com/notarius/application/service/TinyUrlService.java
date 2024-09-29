package com.notarius.application.service;

import com.notarius.application.exception.ApplicationException;
import com.notarius.application.generator.UrlGenerator;
import com.notarius.application.model.entity.TinyUrl;
import com.notarius.application.repository.TinyUrlRepository;
import com.notarius.application.utils.EntityFactory;
import com.notarius.application.utils.UrlHelper;
import com.notarius.application.validation.UrlValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TinyUrlService {

    private final UrlValidator urlValidator;

    private final TinyUrlRepository tinyUrlRepository;

    private final UrlGenerator urlGenerator;

    public TinyUrl findUrl(String url) {
        if(!urlValidator.validateGeneratedUrl(url)) {
            throw new ApplicationException("Invalid Generated Url: " + url);
        }
        log.info("Finding Url: " + url);
        return tinyUrlRepository.findByGeneratedUrl(url).orElse(null);
    }

    public String shorten(String url) {
        if (!urlValidator.validate(url)) {
            throw new ApplicationException("Invalid Url: " + url);
        }

        String normalizedUrl = UrlHelper.normalizeUrl(url);
        log.info("Normalized Url: " + normalizedUrl);

        TinyUrl tinyUrl = tinyUrlRepository.findBySourceUrl(normalizedUrl).orElse(createTinyUrl(normalizedUrl));

        return tinyUrl.getGeneratedUrl();
    }

    private TinyUrl createTinyUrl(String normalizedUrl) {
        String generatedUrl = urlGenerator.generateUrl(normalizedUrl);

        log.info("Generated Url: " + generatedUrl);
        return tinyUrlRepository.save(EntityFactory.createTinyUrl(normalizedUrl, generatedUrl));
    }

}
