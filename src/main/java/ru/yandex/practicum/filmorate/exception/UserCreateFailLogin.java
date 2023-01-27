package ru.yandex.practicum.filmorate.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserCreateFailLogin extends Exception{
    public UserCreateFailLogin(String message) {
        super(message);
    }
}
