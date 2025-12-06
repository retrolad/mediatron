package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.GenreDto;
import com.retrolad.mediatron.model.movie.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre, String lang) {
        return new GenreDto(genre.getId(), genre.getTranslations().get(lang));
    }
}
