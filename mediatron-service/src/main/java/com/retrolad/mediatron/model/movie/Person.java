package com.retrolad.mediatron.model.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String originalName;

    private LocalDate birthday;

    private LocalDate deathday;

    private String placeOfBirth;

    private String profileImage;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "langCode")
    private Map<String, PersonTranslation> translations = new HashMap<>();
}
