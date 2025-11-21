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
public class MoviePersonId implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "role_id")
    private Integer roleId;
}
