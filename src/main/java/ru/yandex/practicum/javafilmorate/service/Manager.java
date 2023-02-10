package ru.yandex.practicum.javafilmorate.service;

import ru.yandex.practicum.javafilmorate.controller.Controller;

import java.util.List;
import java.util.stream.Collectors;

public class Manager<E> extends Controller {

    public List<E> findAll() {
        return (List<E>) storages.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        storages.clear();
    }
}