package ru.yandex.practicum.filmorate.exception;

public class FilmWithEmptyName extends Exception {
    public FilmWithEmptyName(String message) {
        super(message);
    }
}
