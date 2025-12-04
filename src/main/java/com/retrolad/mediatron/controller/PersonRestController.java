package com.retrolad.mediatron.controller;

import com.retrolad.mediatron.dto.PersonDto;
import com.retrolad.mediatron.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonRestController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public PersonDto getById(@PathVariable Long id, @RequestParam(required = false) String lang) {
        return personService.getById(id, lang);
    }
}
