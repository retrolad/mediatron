package com.retrolad.mediatron.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "movie_rating")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRating extends MovieSourceData<Float> {

    @Column(name = "rating", nullable = false)
    private Float rating;

    @Override
    public Float getData() {
        return rating;
    }
}
