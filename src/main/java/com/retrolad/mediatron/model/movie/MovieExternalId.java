package com.retrolad.mediatron.model.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie_external_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieExternalId extends MovieSourceData<String> {

    @Column(name = "external_id")
    private String externalId;

    @Override
    public String getData() {
        return externalId;
    }
}
