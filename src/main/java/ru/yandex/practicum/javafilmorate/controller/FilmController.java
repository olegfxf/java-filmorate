package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;

@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController extends Controller<Film> {
    @PostMapping
    @ResponseBody
    public Film create(@RequestBody final Film film) throws ValidationException {
        validation(film);
        log.info("Creating film {}", film);
        return super.create(film);
    }

    @PutMapping
    public Film update(@RequestBody final Film film) throws ValidationException {
        validation(film);
        for (Long idFilm : storages.keySet()) {
            if (idFilm.equals(film.getId())) {
                log.info("Update film {}", film);
                return super.update(film);
            }
        }


        throw new ValidationException(" Фильм с наименованием " + film.getName() + " неизвестен");
    }


    void validation(Film film) throws ValidationException {
        if (film.getDescription().length() > 200)
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Описание фильма содержит больше"
                    + " 200 символов");

        if (film.getName().isBlank())
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Отрицательная продолжительность"
                    + " фильма");
    }

}

