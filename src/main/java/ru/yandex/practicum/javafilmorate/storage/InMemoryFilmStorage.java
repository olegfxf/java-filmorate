package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    public final HashMap<Long, Film> storages = new HashMap<>();


    Long generateId = -1L;

    @Override
    public List<Film> getAll() {
        //log.info("Выполнен запрос на вывод всех пользователей");
        return storages.values().stream().collect(Collectors.toList());
    }

    @Override
    public Film create(Film film) {
        generateId = film.getId();
        storages.put(generateId, film);

        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException1 {
        generateId = film.getId();

        // if (storages.keySet().stream().filter(e -> e == generateId).findFirst().isPresent())
        storages.keySet().stream().filter(e -> e == generateId).findFirst()
                .orElseThrow(() -> new ValidationException1("Фильма " + film.getName() + " в списке фильмов нет"));

        log.info("Фильм " + film.getName() + " обновлен в списке фильмов");
        storages.put(generateId, film);

        return film;

    }

    @Override
    public void deleteAll() {
        storages.clear();
    }
}
