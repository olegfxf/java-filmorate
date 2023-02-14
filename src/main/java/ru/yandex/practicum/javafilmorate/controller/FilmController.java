package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException400;
import ru.yandex.practicum.javafilmorate.exception.ValidationException404;
import ru.yandex.practicum.javafilmorate.exception.ValidationException500;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Component
@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController {
    FilmStorage filmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }


    @GetMapping
    @ResponseBody
    public List<Film> getAll() {
        log.info("Выполнен запрос на вывод всех фильмов");
        return filmStorage.getAll();
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Film getById(@PathVariable Long id) {
        filmStorage.getAll().stream().filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new ValidationException404("Фильма в списке фильмов нет"));
        log.info("Выполнен запрос на вывод фильма с id = " + id);
        return filmStorage.getById(id);
    }

    @PostMapping
    @ResponseBody
    public Film create(@Valid @RequestBody final Film film) throws ValidationException400 {
        validation(film);
        log.info("Создан фильм {}", film);
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody final Film film) throws ValidationException500 {
        validation(film);
        return filmStorage.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseBody
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseBody
    public Film deletelLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    @ResponseBody
    public List<Film> popularLike(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.popularLike(count);
    }


    void validation(Film film) throws ValidationException400 {
        if (film.getDescription().length() > 200)
            throw new ValidationException400("Описание фильма содержит больше"
                    + " 200 символов");

        if (film.getName().isBlank())
            throw new ValidationException400("Введите наименование фильма");

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException400("Неправильная дата релиза");

        if (film.getDuration() < 0)
            throw new ValidationException400("Отрицательная продолжительность"
                    + " фильма");
    }

}

