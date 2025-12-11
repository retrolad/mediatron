package com.retrolad.mediatron.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auth_user")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    private boolean isActive;

    private Long telegramId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auth_user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<AuthRole> roles = new HashSet<>(); // ROLE_USER, ROLE_ADMIN
}
