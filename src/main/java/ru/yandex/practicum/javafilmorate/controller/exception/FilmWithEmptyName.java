package ru.yandex.practicum.javafilmorate.controller.exception;

public class FilmWithEmptyName extends Exception {
    public FilmWithEmptyName(String message) {
        super(message);
    }
}
