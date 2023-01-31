package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
public class FilmExceptionUpdate {
    public boolean update(HashMap<Integer, Film> films, Film film) throws FilmEmptyName, FilmWithEmptyName,
            FilmFailReleaseDate, FilmFailDurationNegative, FilmUpdateUnknown {


        if (film.getDescription().length() > 200)
            throw new FilmEmptyName("Описание фильиа содержит больше 200 символов");

        if (film.getName().isBlank())
            throw new FilmWithEmptyName("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new FilmFailReleaseDate("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new FilmFailDurationNegative("Отрицательная продолжительность фильма");


        for (Integer idFilm : films.keySet()) {
            if (idFilm.equals(film.getId())) {
                return true;
            }
        }

        throw new FilmUpdateUnknown("Наименование фильма " + film.getName() + " неизвестно");
    }

}
