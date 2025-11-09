package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "production_country")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductionCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String isoCode;

    @Column(name = "russian_name", nullable = false)
    private String ruName;

    @ManyToMany(mappedBy = "productionCountries")
    @ToString.Exclude
    private Set<Movie> movies;

    @ElementCollection
    @JoinTable(
            name = "production_country_translation",
            joinColumns = @JoinColumn(name = "country_id")
    )
    @MapKeyColumn(name = "lang_code")
    @Column(name = "name")
    private Map<String, String> translations = new HashMap<>();
}
