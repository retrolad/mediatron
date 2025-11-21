package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.MoviePersonDto;
import com.retrolad.mediatron.model.MoviePerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoviePersonMapper {

    private final PersonMapper personMapper;

    public MoviePersonDto toDto(MoviePerson moviePerson, String lang) {
        return new MoviePersonDto(
                moviePerson.getPersonId(),
                personMapper.toDto(moviePerson.getPerson(), lang).name(),
                moviePerson.getCharacterName(),
                moviePerson.getRole().getTranslations().get(lang).getDisplayName(),
                moviePerson.getBillingOrder()
        );
    }
}
