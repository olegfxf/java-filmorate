package ru.yandex.practicum.javafilmorate.exception;

public class ValidationException404 extends RuntimeException {
    public ValidationException404(String message) {
        super(message);
    }
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
