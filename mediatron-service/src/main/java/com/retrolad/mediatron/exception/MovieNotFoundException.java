package com.retrolad.mediatron.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends EntityNotFoundException {

    public MovieNotFoundException(Long id) {
        super("Фильм с ID " + id + " не найден");
    }
}
