package com.retrolad.mediatron.service;

import org.springframework.stereotype.Service;

@Service
public class UrlBuilder {

    public String pathFrom(String... paths) {
        return String.join("/", paths);
    }
}
