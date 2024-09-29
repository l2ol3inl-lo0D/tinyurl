package com.notarius.application.utils;

import com.notarius.application.model.entity.TinyUrl;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityFactory {

    public static TinyUrl createTinyUrl(String url, String generatedUrl) {
        return TinyUrl.builder()
                .sourceUrl(url)
                .generatedUrl(generatedUrl)
                .build();
    }

}
