package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.PersonDto;
import com.retrolad.mediatron.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public PersonDto getById(@PathVariable Long id, @RequestParam(required = false) String langCode) {
        return personService.getById(id, langCode);
    }
}
