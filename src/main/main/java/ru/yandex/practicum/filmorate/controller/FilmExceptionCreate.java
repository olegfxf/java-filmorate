package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.FilmEmptyName;
import ru.yandex.practicum.filmorate.exception.FilmFailDurationNegative;
import ru.yandex.practicum.filmorate.exception.FilmFailReleaseDate;
import ru.yandex.practicum.filmorate.exception.FilmWithEmptyName;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;

public class FilmExceptionCreate {

    public boolean create(HashSet<Film> films, Film film) throws FilmEmptyName, FilmWithEmptyName,
            FilmFailReleaseDate, FilmFailDurationNegative {

        if (film.getDescription().length() > 200)
            throw new FilmEmptyName("Описание фильиа содержит больше 200 символов");

        if (film.getName().isBlank())
            throw new FilmWithEmptyName("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new FilmFailReleaseDate("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new FilmFailDurationNegative("Отрицательная продолжительность фильма");


        return true;
    }
}
