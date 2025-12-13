package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.UserMovieDto;
import com.retrolad.mediatron.model.movie.Movie;
import com.retrolad.mediatron.model.user.UserMovieRelation;
import com.retrolad.mediatron.utils.images.ImageSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMovieMapper {

    private final ImageMapper imageMapper;

    public UserMovieDto toDto(UserMovieRelation userMovie, String lang) {
        Movie movie = userMovie.getMovie();
        return new UserMovieDto(
                movie.getId(),
                movie.getTranslations().get(lang).getTitle(),
                imageMapper.toDto(movie.getImages(), lang, ImageSize.FULL).posterPath(),
                movie.getYear(),
                userMovie.getIsRated() != null ? userMovie.getRating() : null
        );
    }
}
