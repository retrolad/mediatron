package com.retrolad.mediatron.model.movie;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "image_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class MovieImageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<MovieImage> images = new HashSet<>();
}
