package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public abstract class Controller<E> {
    protected final HashMap<Long, E> storages = new HashMap<>();

    protected HashMap<Long, E> getStorages() {
        return storages;
    }

    @GetMapping
    public ResponseEntity<List<E>> getAllFilms() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return new ResponseEntity<>(storages.values().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    Long generateId = -1L;

    @PostMapping
    public E create(@RequestBody E data) throws ValidationException {
        generateId = (data instanceof User) ? ((User) data).getId() : ((Film) data).getId();
        storages.put(generateId, data);

        return data;
    }

    @PutMapping
    public ResponseEntity update(@RequestBody E userOrFilm) throws ValidationException {
        generateId = (userOrFilm instanceof User) ? ((User) userOrFilm).getId() : ((Film) userOrFilm).getId();
        if (storages.keySet().stream().filter(e -> e == generateId).findFirst().isPresent())
            storages.put(generateId, userOrFilm);

        return ResponseEntity.ok(userOrFilm);
    }

}
