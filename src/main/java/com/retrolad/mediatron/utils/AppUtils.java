package com.retrolad.mediatron.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppUtils {

    @Value("${app.lang.default}")
    private String defaultLang;

    public String getLangOrDefault(String lang) {
        return lang == null || lang.isBlank() ? defaultLang : lang;
    }
}
