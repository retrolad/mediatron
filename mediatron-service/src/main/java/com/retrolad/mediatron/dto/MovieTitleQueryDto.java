package com.retrolad.mediatron.dto;

public record MovieTitleQueryDto (
        Long id,
        String title,
        int year
) { }
