package com.retrolad.mediatron.model.user;

import com.retrolad.mediatron.security.AuthUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class User {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private AuthUser authUser;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private Set<UserMovieRelation> movieRelations = new HashSet<>();
}
