package com.notarius.application.validation;


import com.notarius.application.model.UrlDescriptor;
import com.notarius.application.utils.UrlHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UrlValidator {

    public boolean validate(String url) {
        return (StringUtils.isNotBlank(url)
                && url.length() <= UrlDescriptor.URL_MAX_SIZE
                && UrlHelper.isUrlValid(url));
    }

    public boolean validateGeneratedUrl(String url) {
        return (StringUtils.isNotBlank(url)
                && url.length() <= UrlDescriptor.GENERATED_URL_MAX_SIZE
                && UrlHelper.isGeneratedUrlValid(url));
    }
}
