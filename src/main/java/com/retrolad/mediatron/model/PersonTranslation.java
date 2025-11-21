package com.retrolad.mediatron.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "person_translation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonTranslation {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private PersonTranslationId id;

    @Column(name = "lang_code", insertable = false, updatable = false)
    private String langCode;

    private String name;

    private String biography;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;
}
