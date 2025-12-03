package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.*;
import com.retrolad.mediatron.model.movie.Genre;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.utils.SourceName;
import com.retrolad.mediatron.utils.images.ImageSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    private final ImageMapper imageMapper;
    private final MoviePersonMapper moviePersonMapper;
    private final SourceMapper sourceMapper;

    public MovieCardDto toMovieCardDto(Movie movie, String lang, ImageSize posterSize) {
        Map<String, Float> ratings = sourceMapper.toDto(movie.getRatings());
        return new MovieCardDto(
                movie.getId(),
                movie.getTranslations().get(lang).getTitle(),
                imageMapper.toDto(movie.getImages(), lang, posterSize).posterPath(),
                movie.getYear(),
                ratings.get(SourceName.IMDB.name().toLowerCase()),
                ratings.get(SourceName.KP.name().toLowerCase())
        );
    }

    public MovieHeroDto toMovieHeroDto(Movie movie, String lang, ImageSize posterSize) {
        Duration duration = Duration.ofMinutes(movie.getRuntime());
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        return new MovieHeroDto(
                movie.getId(),
                movie.getYear(),
                movie.getTranslations().get(lang).getTitle(),
                movie.getOriginalTitle(),
                movie.getTranslations().get(lang).getDescription(),
                String.format("%dh %dmin", hours, minutes),
                movie.getAgeRating(),
                movie.getGenres().stream().map(Genre::getName).toList(),
                imageMapper.toDto(movie.getImages(), lang, posterSize)
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
                        .map(g -> g.getTranslations().get(lang))
                        .toList(),
                movie.getProductionCountries().stream()
                        .map(c -> c.getTranslations().get(lang))
                        .toList(),
                sourceMapper.toDto(movie.getRatings()),
                sourceMapper.toDto(movie.getVotes()),
                sourceMapper.toDto(movie.getExternalIds()),
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
