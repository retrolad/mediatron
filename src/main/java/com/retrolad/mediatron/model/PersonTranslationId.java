package com.retrolad.mediatron.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonTranslationId implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "lang_code", length = 2)
    private String langCode;
}
