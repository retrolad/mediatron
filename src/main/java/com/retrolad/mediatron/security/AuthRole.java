package com.retrolad.mediatron.security;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_role")
@Data
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
}
