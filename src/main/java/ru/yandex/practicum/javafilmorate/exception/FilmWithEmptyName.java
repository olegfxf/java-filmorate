package ru.yandex.practicum.javafilmorate.exception;

public class FilmWithEmptyName extends Exception {
    public FilmWithEmptyName(String message) {
        super(message);
    }
}
