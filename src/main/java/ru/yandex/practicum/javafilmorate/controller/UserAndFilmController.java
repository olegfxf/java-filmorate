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
public abstract class UserAndFilmController<E> {
    private final HashMap<Long, E> usersAndFilms = new HashMap<>();

    protected HashMap<Long, E> getUsersAndFilms() {
        return usersAndFilms;
    }

    @GetMapping
    public ResponseEntity<List<E>> getAllFilms() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return new ResponseEntity<>(usersAndFilms.values().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    Long id = -1L;

    @PostMapping
    public E create(@RequestBody E userOrFilm) throws ValidationException {
        id = (userOrFilm instanceof User)?((User) userOrFilm).getId():((Film) userOrFilm).getId();
        usersAndFilms.put(id, userOrFilm);

        return userOrFilm;
    }

    @PutMapping
    public ResponseEntity update(@RequestBody E userOrFilm) throws ValidationException {
        id = (userOrFilm instanceof User)?((User)userOrFilm).getId():((Film)userOrFilm).getId();
        usersAndFilms.remove(id);
        usersAndFilms.put(id, userOrFilm);

        return ResponseEntity.ok(userOrFilm);
    }


    public List<E> findAll() {
        return usersAndFilms.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        usersAndFilms.clear();
    }
}
