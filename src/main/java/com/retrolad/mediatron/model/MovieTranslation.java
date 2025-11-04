package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_translation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieTranslation {

    @EmbeddedId
    private MovieTranslationId id;      // составной ключ (movie_id + lang_code)

    @Column(name = "lang_code", insertable=false, updatable=false)
    private String langCode;

    private String title;
    private String description;
    private String tagline;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")                  // movieId из EmbeddedId
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        MovieTranslation other = (MovieTranslation) o;

        return id.equals(other.getId());
    }
}
