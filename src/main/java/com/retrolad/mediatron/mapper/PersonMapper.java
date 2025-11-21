package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.PersonDto;
import com.retrolad.mediatron.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    @Value("${app.images.tmdb-images-url}")
    private String tmdbImagesUrl;

    public PersonDto toDto(Person person, String langCode) {
        return new PersonDto(
                person.getId(),
                person.getTranslations().get(langCode).getName(),
                person.getOriginalName(),
                person.getTranslations().get(langCode).getBiography(),
                person.getPlaceOfBirth(),
                person.getBirthday(),
                person.getDeathday(),
                tmdbImagesUrl + person.getProfileImage()
        );
    }
}
