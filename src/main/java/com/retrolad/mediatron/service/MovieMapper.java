package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.model.Genre;
import com.retrolad.mediatron.model.Movie;

public class MovieMapper {

    public static MovieDto toDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getReleaseYear(),
                movie.getDescription(),
                movie.getDirector(),
                movie.getDuration(),
                movie.getGenres().stream().map(Genre::getName).toList()
        );
    }
}
