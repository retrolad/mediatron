package com.retrolad.mediatron.model.movie;

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
    private Long movieId;

    @Column(name = "lang_code", length = 2)
    private String langCode;
}
