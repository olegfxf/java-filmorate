package ru.yandex.practicum.filmorate.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserUpdateUnknown extends Exception {
    public UserUpdateUnknown(String message) {
        super(message);
    }
}
