package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.Film;
import java.util.List;

public interface FilmStorage {
    List<Film> getAll();
    Film create(Film film);
    Film update(Film film) throws ValidationException1;
    void deleteAll();
}
