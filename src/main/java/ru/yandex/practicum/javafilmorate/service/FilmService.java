package ru.yandex.practicum.javafilmorate.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class FilmService {
    InMemoryFilmStorage inMemoryFilmStorage;


    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film addLike(final Long id, final Long userId) {
        Film film = inMemoryFilmStorage.storages.get(id);
        film.likes.add(userId);
        inMemoryFilmStorage.storages.put(id, film);

        return film;
    }

    public Film deleteLike(final Long id, final Long userId) {
        Film film = inMemoryFilmStorage.storages.get(id);
        film.likes.remove(userId);
        inMemoryFilmStorage.storages.remove(id, film);

        return film;
    }

    public List<Film> popularLike(Integer count) {
        List<Film> films = inMemoryFilmStorage.getAll();
        Collections.sort(films);

        count = (films.size()>count)?count:films.size();
        films.subList(0, count);

        return films;
    }

}
