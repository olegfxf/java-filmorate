package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
@RequestMapping(value = "/films")
@RestController
public class FilmController extends Controller<Film> {

    @Override
    public Film update(@RequestBody Film film) throws FilmFailReleaseDate,
            FilmWithEmptyName, FilmFailDurationNegative, FilmEmptyName,
            FilmUpdateUnknown {
        boolean isUpdateFilm;

        FilmExceptionUpdate filmExceptionUpdate = new FilmExceptionUpdate();
        isUpdateFilm = filmExceptionUpdate.update(objs, film);
        if (isUpdateFilm)
            for (Film film1 : objs) {
                if (film1.getId() == film.getId()) {
                    objs.remove(film1);
                    objs.add(film);
                    log.info("Фильм " + film.getName() + " обновлен");
                    return film;
                }
            }

        return null;
    }

}
