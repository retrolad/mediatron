package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_translation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class MovieTranslation {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private MovieTranslationId id;      // составной ключ (movie_id + lang_code)

    @Column(name = "lang_code", insertable=false, updatable=false)
    private String langCode;

    private String title;
    private String description;
    private String tagline;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")                  // movieId из EmbeddedId
    @JoinColumn(name = "movie_id")
    @ToString.Exclude
    private Movie movie;
}
