package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.model.Genre;
import com.retrolad.mediatron.model.Movie;

public class MovieMapper {

    public static MovieDto toDto(Movie movie, String lang) {
        return new MovieDto(
                movie.getId(),
                movie.getOriginalTitle(),
                movie.getYear(),
                movie.getRuntime(),
                movie.getTranslations().get(lang).getTitle(),
                movie.getTranslations().get(lang).getDescription(),
                movie.getTranslations().get(lang).getTagline(),
                movie.getRatingMpaa(),
                movie.getAgeRating(),
                movie.getGenres().stream()
                        .map(Genre::getName)
                        .toList()
        );
    }
}
