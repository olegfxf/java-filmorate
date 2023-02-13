package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.exception.ValidationException500;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmControllerTest {
    InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    @Test
    void getAll() {
        filmStorage.deleteAll();
        Film film = new Film().testFilm();
        filmStorage.create(film);
        assertEquals(1, filmStorage.getAll().size(), "Длина списка фильмов не равна 1");
    }

    @Test
    void create() {
        filmStorage.deleteAll();
        Film filmCreate = new Film().testFilm();
        filmStorage.create(filmCreate);
        assertEquals(1, filmStorage.getAll().size(), "Фильм не добавлен в список фильмов");
    }

    @Test
    void update() throws ValidationException500 {
        filmStorage.deleteAll();
        Film filmCreate = new Film().testFilm();
        filmStorage.create(filmCreate);
        Long id = filmStorage.getAll().stream().map(e->e.getId()).findFirst().get();

        Film filmUpdate = new Film().testFilm();
        String userUpdateName = filmUpdate.getName();
        filmUpdate.setId(id);
        filmStorage.update(filmUpdate);
        ArrayList<Film> films = (ArrayList<Film>) filmStorage.getAll().stream().collect(Collectors.toList());
        assertEquals(userUpdateName, films.get(0).getName(), "Обновление фильма прошло не успешно ");
    }

}