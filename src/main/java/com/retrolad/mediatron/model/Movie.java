package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String originalTitle;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int runtime;

    private String ratingMpaa;

    private Short ageRating;

    private Long budget;

    private String originalLanguage;

    private LocalDate releaseDate;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "langCode")
    private Map<String, MovieTranslation> translations = new HashMap<>();

//    @ManyToMany
//    @JoinTable(
//            name = "movie_genres",
//            joinColumns = @JoinColumn(name = "movie_id"),
//            inverseJoinColumns = @JoinColumn(name = "genre_id")
//    )
//    @JsonManagedReference
//    private Set<Genre> genres = new HashSet<>();

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
}
