package com.retrolad.mediatron.dto;

public record UserMovieDto (
        Long id,
        String title,
        String posterUrl,
        int year,
        Integer userRating
) { }
