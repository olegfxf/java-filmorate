package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
public class FilmExceptionUpdate {
    public boolean update(HashSet<Film> films, Film film) throws FilmEmptyName, FilmWithEmptyName,
            FilmFailReleaseDate, FilmFailDurationNegative, FilmUpdateUnknown {


        if (film.getDescription().length() > 200)
            throw new FilmEmptyName("Описание фильма содержит больше 200 символов");

        if (film.getName().isBlank())
            throw new FilmWithEmptyName("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new FilmFailReleaseDate("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new FilmFailDurationNegative("Отрицательная продолжительность фильма");

        System.out.println("For Update film.getId() = " + film.getId() );
        System.out.println("Current Id in the listFilm is [films.getId()] = ");
        films.stream().forEach(e-> System.out.println(e.getId()));
        for (Film film1 : films) {
            if (film1.getId() == film.getId()) {
                return true;
            }
        }

        throw new FilmUpdateUnknown("Наименование фильма " + film.getName() + " неизвестно");
    }

}
