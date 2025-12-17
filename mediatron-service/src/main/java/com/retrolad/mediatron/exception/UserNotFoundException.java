package com.retrolad.mediatron.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("Пользователь с ID " + id + " не найден");
    }
}
