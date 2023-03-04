package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.ValidationException404;
import ru.yandex.practicum.javafilmorate.exception.ValidationException500;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> storages = new HashMap<>();
    Long generateId = 1L;

    @Override
    public List<Film> getAll() {
        log.info("Выполнен запрос на вывод всех фильмов");
        return storages.values().stream().collect(Collectors.toList());
    }

    @Override
    public Film getById(Long id) {
        storages.keySet().stream().filter(e -> e.equals(id)).findFirst()
                .orElseThrow(() -> new ValidationException404("Фильма с id = " + id
                        + " в списке фильмов нет"));

        return storages.get(id);
    }

    @Override
    public Film create(Film film) {
        film.setId(generateId++);
        storages.put(film.getId(), film);

        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException500 {
        storages.keySet().stream().filter(e -> e.equals(film.getId())).findFirst()
                .orElseThrow(() -> new ValidationException500("Фильма " + film.getName() + " в списке фильмов нет"));
        log.info("Фильм " + film.getName() + " обновлен в списке фильмов");
        storages.put(film.getId(), film);

        return film;
    }

    @Override
    public void deleteAll() {
        storages.clear();
    }
}
