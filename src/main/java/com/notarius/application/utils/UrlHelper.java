package com.notarius.application.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class UrlHelper {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String HTTPS_PORT = ":443";
    private static final String WORLD_WIDE_WEB = "www.";
    private static final String CHARACTER_MATCHER = "^(https?://)?(www\\.)?([\\w-]+\\.)+\\w{2,}(/[\\w-./?%&=]*)?$";
    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("^[a-zA-Z0-9]+$");

    public static boolean containsProtocol(String url) {
        return url.startsWith(HTTP) || url.startsWith(HTTPS);
    }

    public static boolean isUrlValid(String url) {
        return url.matches(CHARACTER_MATCHER);
    }

    public static boolean isGeneratedUrlValid(String url) {
        return ALLOWED_CHARACTERS.matcher(url).find();
    }

    /**
     * Transform the url int a low case version with always http:// or https:// and www. at the beginning.
     *
     * @param url the url to be normalized
     * @return the normalized url
     */
    public static String normalizeUrl(String url) {
        String normalizedString = url.toLowerCase();
        if (!containsProtocol(normalizedString)) {
            if (!normalizedString.contains(WORLD_WIDE_WEB)) {
                normalizedString = WORLD_WIDE_WEB + normalizedString;
            }

            if (normalizedString.contains(HTTPS_PORT)) {
                normalizedString = HTTPS + normalizedString;
            } else {
                normalizedString = HTTP + normalizedString;
            }
        }

        return normalizedString;
    }

}
