package ru.yandex.practicum.javafilmorate.controller;



import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class FilmExceptionCreate {

    public boolean create(HashMap<Integer, Film> films, Film film) throws FilmEmptyName, FilmWithEmptyName,
            FilmFailReleaseDate, FilmFailDurationNegative, ValidationException {

        if (film.getDescription().length() > 200)
            throw new ValidationException("Описание фильма содержит больше 200 символов");

        if (film.getName().isBlank())
            throw new FilmWithEmptyName("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new FilmFailReleaseDate("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new FilmFailDurationNegative("Отрицательная продолжительность фильма");


        return true;
    }
}
