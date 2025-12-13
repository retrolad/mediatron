package com.retrolad.mediatron.model.user;

import com.retrolad.mediatron.model.movie.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Entity
@Table(name = "user_movie_relation")
@Data
@EqualsAndHashCode(of = "id")
public class UserMovieRelation {

    @EmbeddedId
    private UserMovieRelationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    private Boolean isWatched;
    private Boolean isInWatchlist;
    private Boolean isFavourite;
    private Boolean isRated;

    private Integer rating;

    private Instant createdAt;
}
