package com.retrolad.mediatron.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieTranslationId implements Serializable {

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "lang_code")
    private String langCode;
}
