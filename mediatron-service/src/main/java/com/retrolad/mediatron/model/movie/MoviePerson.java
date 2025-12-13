package com.retrolad.mediatron.model.movie;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MoviePerson {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private MoviePersonId id;

    @Column(name = "person_id", updatable = false, insertable = false)
    private Long personId;

    private String characterName;

    private Short billingOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @MapsId("movieId")
    @ToString.Exclude
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @MapsId("personId")
    @ToString.Exclude
    private Person person;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    @ToString.Exclude
    private Role role;
}
