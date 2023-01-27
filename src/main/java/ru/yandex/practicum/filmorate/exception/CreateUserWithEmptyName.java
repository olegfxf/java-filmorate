package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CreateUserWithEmptyName extends Exception{
    public CreateUserWithEmptyName(String message) {
        super(message);
    }
}

