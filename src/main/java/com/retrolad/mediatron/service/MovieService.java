package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.ImageSize;
import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.mapper.MovieMapper;
import com.retrolad.mediatron.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Value("${app.lang.default}")
    private String DEFAULT_LANG = "ru";

    public List<Integer> getAllYears() {
        return movieRepository.findAllYears();
    }

    public List<MovieDto> getByYear(Integer year, ImageSize size) {
        return movieRepository.findByYearWithTranslation(year, DEFAULT_LANG).stream()
                .map(m -> movieMapper.toDto(m, DEFAULT_LANG, size))
                .toList();
    }

    public MovieDto getById(Long id, String lang, ImageSize size) {
        return movieRepository.findByIdWithTranslation(id, lang)
                .map(m -> movieMapper.toDto(m, lang, size)).orElseThrow();
    }
}
