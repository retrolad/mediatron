package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.*;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.service.MoviePersonOrderComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    private final ImageMapper imageMapper;
    private final MoviePersonMapper moviePersonMapper;
    private final MoviePersonOrderComparator orderComparator;

    public MovieCardDto toMovieCardDto(Movie movie, String lang, ImageSize posterSize) {
        Map<String, Float> ratings = SourceMapper.toDto(movie.getRatings());
        return new MovieCardDto(
                movie.getId(),
                movie.getTranslations().get(lang).getTitle(),
                imageMapper.toDto(movie.getImages(), lang, posterSize).posterPath(),
                movie.getYear(),
                ratings.get(SourceName.IMDB.name().toLowerCase()),
                ratings.get(SourceName.KP.name().toLowerCase())
        );
    }

    public MovieDto toDto(Movie movie, String lang, ImageSize posterSize) {
        List<MoviePersonDto> persons = movie.getPersons().stream()
                .map(p -> moviePersonMapper.toDto(p, lang))
                .toList();
        return new MovieDto(
                movie.getId(),
                movie.getOriginalTitle(),
                movie.getYear(),
                movie.getRuntime(),
                movie.getTranslations().get(lang).getTitle(),
                movie.getTranslations().get(lang).getDescription(),
                movie.getTranslations().get(lang).getTagline(),
                movie.getRatingMpaa(),
                movie.getBudget(),
                movie.getAgeRating(),
                movie.getGenres().stream()
                        .map(g -> GenreMapper.toDto(g, lang))
                        .toList(),
                movie.getProductionCountries().stream()
                        .map(c -> CountryMapper.toDto(c, lang))
                        .toList(),
                SourceMapper.toDto(movie.getRatings()),
                SourceMapper.toDto(movie.getVotes()),
                SourceMapper.toDto(movie.getExternalIds()),
                imageMapper.toDto(movie.getImages(), lang, posterSize),
                persons.stream()
                        .filter(p -> p.role().equals("Actor"))
                        .sorted(Comparator.comparingInt(MoviePersonDto::order))
                        .toList(),
                persons.stream()
                        .filter(p -> !p.role().equals("Actor"))
                        .collect(Collectors.groupingBy(MoviePersonDto::role))
        );
    }
}
