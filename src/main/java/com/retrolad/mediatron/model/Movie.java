package com.retrolad.mediatron.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String originalTitle;

    @Column(nullable = false)
    private int releaseYear;

    private String description;

    private Integer kinopoiskId;

    private Integer imdbId;

    private Short oscarCount;

    private Float kinopoiskRating;

    private Float imdbRating;

    private Float interestGroupRating;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private short duration;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonManagedReference
    private Set<Genre> genres = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Movie other = (Movie) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Movie{id=%d, title='%s', originalTitle='%s', releaseYear=%d, director='%s', duration=%d}",
                id, title, originalTitle, releaseYear, director, duration);
    }
}
