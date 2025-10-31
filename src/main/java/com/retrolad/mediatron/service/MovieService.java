package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.MovieDto;
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
                .map(MovieMapper::toDto)
                .toList();
    }

    public List<MovieDto> getByYear(int year) {
        return movieRepository.getMoviesByReleaseYear(year).stream()
                .map(MovieMapper::toDto)
                .toList();
    }
}
