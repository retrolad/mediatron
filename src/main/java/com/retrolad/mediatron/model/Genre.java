package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "genre")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    @ToString.Exclude
    private Set<Movie> movies = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "genre_translation",
            joinColumns = @JoinColumn(name = "genre_id")
    )
    @MapKeyColumn(name = "lang_code")
    @Column(name = "name")
    private Map<String, String> translations = new HashMap<>();
}
