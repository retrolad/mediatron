package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.model.movie.Genre;

public class GenreMapper {

    public static String toDto(Genre g, String lang) {
        return g.getTranslations().get(lang);
    }
}
