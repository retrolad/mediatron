package com.retrolad.mediatron.service;

import com.retrolad.mediatron.utils.AppUtils;
import com.retrolad.mediatron.utils.images.ImageSize;
import com.retrolad.mediatron.dto.MovieCardDto;
import com.retrolad.mediatron.dto.MovieDto;
import com.retrolad.mediatron.dto.MovieHeroDto;
import com.retrolad.mediatron.mapper.MovieMapper;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final AppUtils appUtils;

    public List<Integer> getAllYears() {
        return movieRepository.findAllYears();
    }

    public List<MovieDto> getByYear(Integer year, ImageSize size, String lang) {
        String l = appUtils.getLangOrDefault(lang);
        return movieRepository.findByYearWithTranslation(year, l).stream()
                .map(m -> movieMapper.toDto(m, l, size))
                .toList();
    }

    public MovieDto getById(Long id, String lang, ImageSize size) {
        String l = appUtils.getLangOrDefault(lang);
        return movieRepository.findByIdWithTranslation(id, l)
                .map(m -> movieMapper.toDto(m, l, size)).orElseThrow();
    }

    public List<MovieCardDto> getMovieCards(Pageable pageable, String lang) {
        String l = appUtils.getLangOrDefault(lang);
        return movieRepository.findMovieCards(l, pageable)
                .map(m -> movieMapper.toMovieCardDto(m, l, ImageSize.FULL))
                .toList();
    }

    public List<MovieHeroDto> getMovieHero(Pageable pageable, String lang) {
        String l = appUtils.getLangOrDefault(lang);

        // Здесь должен быть запрос к TMDB, чтобы получить фильмы,
        // которые идут в кино. Пока берем любые ids
        List<Long> ids = movieRepository.findMovieIdsByLang(l, pageable);
        List<Movie> movies = movieRepository.findMovieByIds(ids);

        return movies.stream()
                .map(m -> movieMapper.toMovieHeroDto(m, l, ImageSize.FULL))
                .toList();
    }
}
