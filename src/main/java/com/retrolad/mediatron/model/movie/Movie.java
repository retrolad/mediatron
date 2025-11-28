package com.retrolad.mediatron.model.movie;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
        name = "Movie.movieCard",
        attributeNodes = {
                @NamedAttributeNode(value = "translations"),
                @NamedAttributeNode(value = "images"),
                @NamedAttributeNode(value = "images"),
                @NamedAttributeNode(value = "ratings"),
                @NamedAttributeNode(value = "ratings")
        }
)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(nullable = false)
    @ToString.Include
    private String originalTitle;

    @Column(nullable = false)
    @ToString.Include
    private int year;

    @Column(nullable = false)
    @ToString.Include
    private int runtime;

    @ToString.Include
    private String ratingMpaa;

    @ToString.Include
    private Short ageRating;

    @ToString.Include
    private Long budget;

    @ToString.Include
    private String originalLanguage;

    @ToString.Include
    private LocalDate releaseDate;

    @ToString.Include
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "langCode")
    private Map<String, MovieTranslation> translations = new HashMap<>();

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonManagedReference
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "movie_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<ProductionCountry> productionCountries = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieRating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieVotes> votes = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieExternalId> externalIds = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieImage> images = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MoviePerson> persons = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Movie other = (Movie) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
