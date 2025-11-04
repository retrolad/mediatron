package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.model.Movie;

public class MovieMapper {

    public static MovieDto toDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getOriginalTitle(),
                movie.getYear(),
                movie.getRuntime(),
                movie.getRatingMpaa(),
                movie.getAgeRating()
        );
    }
}
