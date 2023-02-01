package ru.yandex.practicum.javafilmorate.controller;



import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;


public class FilmExceptionCreate {

    public boolean create(HashMap<Integer, Film> films, Film film) throws ValidationException {

        if (film.getDescription().length() > 200)
            throw new ValidationException("Описание фильма содержит больше 200 символов");

        if (film.getName().isBlank())
            throw new  ValidationException("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new  ValidationException("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new  ValidationException("Отрицательная продолжительность фильма");


        return true;
    }
}
