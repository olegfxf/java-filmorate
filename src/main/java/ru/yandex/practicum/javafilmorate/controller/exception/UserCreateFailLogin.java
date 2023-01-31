package ru.yandex.practicum.javafilmorate.controller.exception;

public class UserCreateFailLogin extends Exception {
    public UserCreateFailLogin(String message) {
        super(message);
    }
}
