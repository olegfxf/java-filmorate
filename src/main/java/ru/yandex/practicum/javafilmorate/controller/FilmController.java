package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@RequestMapping("/films")
@RestController
@Slf4j
public class FilmController  {
    InMemoryFilmStorage filmStorage;
    FilmService filmService;
    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService= filmService;
    }



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

    @PutMapping("/{id}/like/{userId}")
    @ResponseBody
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        System.out.println(" qqqq " + userId + " ffff " + id);
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
        System.out.println("qqqq "+count);
        return filmService.popularLike(count);
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

