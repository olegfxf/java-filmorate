package ru.yandex.practicum.javafilmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.ValidationException404;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        if (id < 0 || userId < 0)
            throw new ValidationException404("Отрицательный идентификатор");
        Film film = inMemoryFilmStorage.storages.get(id);
        film.likes.remove(userId);
        inMemoryFilmStorage.storages.put(id, film);

        return film;
    }

    public List<Film> popularLike(Integer count) {
        List<Film> films = inMemoryFilmStorage.getAll();
        Collections.reverse(films);
        count = (films.size() > count) ? count : films.size();

        return films.stream().limit(count).collect(Collectors.toList());
    }

}
