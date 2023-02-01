package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
//import javax.validation.ValidationException;
import java.time.LocalDate;

@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController extends Controller<Film>{

    @PostMapping
    public Film create(@Valid @RequestBody final Film film) throws ru.yandex.practicum.javafilmorate.exception.ValidationException {
        validationCreate(film);
        log.info("Creating film {}", film);
        return super.create(film);
    }

// TODO методы контроллера

    void validationCreate(Film film) throws ValidationException {
//        if(film.getName() == null || film.getName().isEmpty()){
//            throw new ValidationException("Film name invalid");
//        }

        if (film.getDescription().length() > 200)
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Описание фильма содержит больше 200 символов");

        if (film.getName().isBlank())
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new ru.yandex.practicum.javafilmorate.exception.ValidationException("Отрицательная продолжительность фильма");


    }

    // TODO валидация
}

