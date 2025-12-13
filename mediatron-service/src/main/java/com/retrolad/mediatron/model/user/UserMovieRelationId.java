package com.retrolad.mediatron.model.user;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMovieRelationId implements Serializable {
    private Long userId;
    private Long movieId;
}
