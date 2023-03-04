package ru.yandex.practicum.javafilmorate.exception;

public class ValidationException400 extends RuntimeException {
    public ValidationException400(String message) {
        super(message);
    }
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
