package ru.yandex.practicum.filmorate.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserCreateFailEmail extends Exception{
    public UserCreateFailEmail(String message){ super(message); }
}

