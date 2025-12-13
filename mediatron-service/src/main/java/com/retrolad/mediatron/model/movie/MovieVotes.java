package com.retrolad.mediatron.model.movie;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_votes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieVotes extends MovieSourceData<Integer> {

    @Column(name = "votes", nullable = false)
    private Integer votes;

    @Override
    public Integer getData() {
        return votes;
    }
}
