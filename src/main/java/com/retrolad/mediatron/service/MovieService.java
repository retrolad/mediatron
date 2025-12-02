package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.ImageSize;
import com.retrolad.mediatron.dto.MovieCardDto;
import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.dto.MovieHeroDto;
import com.retrolad.mediatron.mapper.MovieMapper;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Value("${app.lang.default}")
    private String defaultLang;

    public List<Integer> getAllYears() {
        return movieRepository.findAllYears();
    }

    public List<MovieDto> getByYear(Integer year, ImageSize size, String lang) {
        String l = lang == null || lang.isBlank() ? defaultLang : lang;
        return movieRepository.findByYearWithTranslation(year, l).stream()
                .map(m -> movieMapper.toDto(m, l, size))
                .toList();
    }

    public MovieDto getById(Long id, String lang, ImageSize size) {
        String l = lang == null || lang.isBlank() ? defaultLang : lang;
        return movieRepository.findByIdWithTranslation(id, l)
                .map(m -> movieMapper.toDto(m, l, size)).orElseThrow();
    }

    public List<MovieCardDto> getMovieCards(Pageable pageable) {
        return movieRepository.findMovieCards("ru", pageable)
                .map(m -> movieMapper.toMovieCardDto(m, defaultLang, ImageSize.FULL))
                .toList();
    }

    public List<MovieHeroDto> getMovieHero(Pageable pageable) {
        // Здесь должен быть запрос к TMDB, чтобы получить фильмы,
        // которые идут в кино. Пока берем любые ids
        List<Long> ids = movieRepository.findMovieIdsByLang(defaultLang, pageable);
        List<Movie> movies = movieRepository.findMovieByIds(ids);

        return movies.stream()
                .map(m -> movieMapper.toMovieHeroDto(m, defaultLang, ImageSize.FULL))
                .toList();
    }
}
