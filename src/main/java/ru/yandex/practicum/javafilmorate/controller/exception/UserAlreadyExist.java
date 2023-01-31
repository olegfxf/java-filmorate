package ru.yandex.practicum.javafilmorate.controller.exception;

public class UserAlreadyExist extends Exception {
    public UserAlreadyExist(String message) {
        super(message);
    }
}

