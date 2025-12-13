package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.PersonDto;
import com.retrolad.mediatron.mapper.PersonMapper;
import com.retrolad.mediatron.repository.PersonRepository;
import com.retrolad.mediatron.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final AppUtils appUtils;

    public PersonDto getById(Long id, String lang) {
        return personRepository.findById(id)
                .map(person -> personMapper.toDto(person, appUtils.getLangOrDefault(lang)))
                .orElseThrow();
    }
}
