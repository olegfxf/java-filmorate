package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController  {
    InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();


    @GetMapping
    @ResponseBody
    public List<Film> getAll() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return filmStorage.getAll();
    }

    @PostMapping
    @ResponseBody
    public Film create(@Valid @RequestBody final Film film) throws ValidationException1 {
        validation(film);
        log.info("Creating film {}", film);
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody final Film film) throws ValidationException1 {
        validation(film);
        return filmStorage.update(film);
    }


    void validation(Film film) throws ValidationException1 {
        if (film.getDescription().length() > 200)
            throw new ValidationException1("Описание фильма содержит больше"
                    + " 200 символов");

        if (film.getName().isBlank())
            throw new ValidationException1("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException1("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new ValidationException1("Отрицательная продолжительность"
                    + " фильма");
    }

}

