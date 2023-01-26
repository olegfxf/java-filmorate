package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserCreateFailLogin extends Exception{
    public UserCreateFailLogin(String message) {
        super(message);
    }
}
