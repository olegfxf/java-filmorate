package ru.yandex.practicum.javafilmorate.controller.exception;

public class FilmFailDurationNegative extends Exception {
    public FilmFailDurationNegative(String message) {
        super(message);
    }
}
