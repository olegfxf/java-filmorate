package ru.yandex.practicum.javafilmorate.controller.exception;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }

}
