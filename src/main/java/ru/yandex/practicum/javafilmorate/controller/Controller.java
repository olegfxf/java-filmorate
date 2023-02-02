package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Uid;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@Slf4j
public abstract class Controller<E> {
    private final HashMap<Long, E> objs = new HashMap<>();

    protected HashMap<Long, E> getObjs() {
        return objs;
    }

    @GetMapping
    public ResponseEntity<List<E>> getAllFilms() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return new ResponseEntity<>(objs.values().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    Long id = -1L;

    @PostMapping
    public E create(@RequestBody E obj) throws ValidationException {

        id = (obj instanceof User)?((User) obj).getId():((Film) obj).getId();

        objs.put(id, obj);

        return obj;
    }


    @PutMapping
    public ResponseEntity update(@RequestBody E obj) throws ValidationException {

        id = (obj instanceof User)?((User)obj).getId():((Film)obj).getId();

        objs.remove(id);
        objs.put(id, obj);

        return ResponseEntity.ok(obj);
    }


    public List<E> findAll() {
        return objs.values().stream().collect(Collectors.toList());
    }

    public void deleteAll() {
        objs.clear();
    }
}
