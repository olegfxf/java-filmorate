package ru.yandex.practicum.javafilmorate.controller.exception;

public class UserUpdateUnknown extends Exception {
    public UserUpdateUnknown(String message) {
        super(message);
    }
}
