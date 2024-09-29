package com.notarius.application.generator;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UrlGenerator {

    private static final int[] GENERATED_HASH_RANGE = {0, 10};

    public String generateUrl(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("Url cannot be null or empty");
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(GENERATED_HASH_RANGE[0], GENERATED_HASH_RANGE[1]);
    }
}
