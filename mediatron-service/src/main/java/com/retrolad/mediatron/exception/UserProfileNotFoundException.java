package com.retrolad.mediatron.exception;

public class UserProfileNotFoundException extends RuntimeException {

    public UserProfileNotFoundException(Long id) {
        super("У пользователя c ID " + id + " нет профиля");
    }
}
