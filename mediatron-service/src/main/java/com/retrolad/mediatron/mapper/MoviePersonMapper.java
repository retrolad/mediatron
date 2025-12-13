package com.retrolad.mediatron.mapper;

import com.retrolad.mediatron.dto.MoviePersonDto;
import com.retrolad.mediatron.model.movie.MoviePerson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoviePersonMapper {

    private final PersonMapper personMapper;
    private final RoleMapper roleMapper;

    public MoviePersonDto toDto(MoviePerson moviePerson, String lang) {
        return new MoviePersonDto(
                moviePerson.getPersonId(),
                personMapper.toDto(moviePerson.getPerson(), lang).name(),
                moviePerson.getCharacterName(),
                roleMapper.toDto(moviePerson.getRole(), "en"),
                moviePerson.getBillingOrder(),
                moviePerson.getPerson().getProfileImage()
        );
    }
}
