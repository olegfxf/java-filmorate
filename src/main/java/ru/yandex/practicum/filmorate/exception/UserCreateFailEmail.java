package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserCreateFailEmail extends Exception{
    public UserCreateFailEmail(String message){ super(message); }
}

