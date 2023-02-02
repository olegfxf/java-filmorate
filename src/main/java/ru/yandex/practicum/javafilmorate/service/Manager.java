package ru.yandex.practicum.javafilmorate.service;

import ru.yandex.practicum.javafilmorate.controller.UserAndFilmController;
import java.util.List;
import java.util.stream.Collectors;

public class Manager<E> extends UserAndFilmController {

    public List<E> findAll() {
        return (List<E>) usersAndFilms.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        usersAndFilms.clear();
    }
}