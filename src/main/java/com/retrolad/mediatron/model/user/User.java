package com.retrolad.mediatron.model.user;

import com.retrolad.mediatron.security.AuthUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
