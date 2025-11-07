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
    private static final String DEFAULT_LANG = "ru";

    public List<Integer> getAllYears() {
        return movieRepository.findAllYears();
    }

    public List<MovieDto> getByYear(Integer year) {
        return movieRepository.findByYearWithTranslation(year, DEFAULT_LANG).stream()
                .map(m -> MovieMapper.toDto(m, DEFAULT_LANG))
                .toList();
    }

    public MovieDto getById(Long id, String lang) {
        return movieRepository.findByIdWithTranslation(id, lang)
                .map(m -> MovieMapper.toDto(m, lang)).orElseThrow();
    }
}
