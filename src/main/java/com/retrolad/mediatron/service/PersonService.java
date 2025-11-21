package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.PersonDto;
import com.retrolad.mediatron.mapper.PersonMapper;
import com.retrolad.mediatron.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Value("${app.lang.default}")
    private String defaultLang;

    public PersonDto getById(Long id, String langCode) {
        return personRepository.findById(id)
                .map(person -> personMapper.toDto(person, langCode == null || langCode.isBlank() ? defaultLang : langCode))
                .orElseThrow();
    }
}
