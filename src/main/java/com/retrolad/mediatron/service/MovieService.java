package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.model.Genre;
import com.retrolad.mediatron.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;

    public List<MovieDto> getAll() {
        return movieRepository.findAll().stream()
                .map(movie-> new MovieDto(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getOriginalTitle(),
                        movie.getReleaseYear(),
                        movie.getDescription(),
                        movie.getDirector(),
                        movie.getDuration(),
                        movie.getGenres().stream().map(Genre::getName).toList()
                ))
                .toList();
    }
}
