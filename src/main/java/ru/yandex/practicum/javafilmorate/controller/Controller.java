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
    private final HashMap<Integer, E> objs = new HashMap<>();

    protected HashMap<Integer, E> getObjs() {
        return objs;
    }

    @GetMapping
    public ResponseEntity<List<E>> getAllFilms() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return new ResponseEntity<>(objs.values().stream().collect(Collectors.toList()), HttpStatus.OK);
    }


    Integer id = -1;

    @PostMapping
    public E create(@RequestBody E obj) throws ValidationException {

//        boolean isCreateUser = false;
//        boolean isCreateFilm = false;
//
//
//        if (obj instanceof User) {
//            UserExceptionCreate userExceptionCreate = new UserExceptionCreate();
//            isCreateUser = userExceptionCreate.create((HashMap<Integer, User>) objs, (User) obj);
//            id = ((User) obj).getId();
//        } else if (obj instanceof Film) {
//            FilmExceptionCreate filmExceptionCreate = new FilmExceptionCreate();
//            isCreateFilm = filmExceptionCreate.create((HashMap<Integer, Film>) objs, (Film) obj);
//            id = ((Film) obj).getId();
//        }
//
//        if (id == -1) id = Uid.getUid();
//
//
//        if (isCreateUser || isCreateFilm)
//            objs.put(id, obj);
//
//        return obj;


        if (obj instanceof User) {
            id = ((User) obj).getId();
        } else if (obj instanceof Film)
            id = ((Film) obj).getId();

//        id = (obj instanceof User)?((User) obj).getId():((Film) obj).getId();

        objs.put(id, obj);

        return obj;


    }


    @PutMapping
    public ResponseEntity update(@RequestBody E obj) throws ValidationException {
//        boolean isUpdateUser = false;
//        boolean isUpdateFilm = false;
//
//        if (obj instanceof User) {
//            UserExceptionUpdate updateUser = new UserExceptionUpdate();
//            isUpdateUser = updateUser.update((HashMap<Integer, User>) objs, (User) obj);
//            if (isUpdateUser) {
//                Integer id = ((User) obj).getId();
//                ((HashMap<Integer, User>) objs).remove(id);
//                ((HashMap<Integer, User>) objs).put(id, (User) obj);
//                log.info("Пользователь " + ((User) obj).getName() + " обновлен");
//                //return new ResponseEntity<>(obj, HttpStatus.OK);
//
//            }
//        } else if (obj instanceof Film) {
//            FilmExceptionUpdate filmExceptionUpdate = new FilmExceptionUpdate();
//            isUpdateFilm = filmExceptionUpdate.update((HashMap<Integer, Film>) objs, (Film) obj);
//            if (isUpdateFilm) {
//                Integer id = ((Film) obj).getId();
//                ((HashMap<Integer, Film>) objs).remove(id);
//                ((HashMap<Integer, Film>) objs).put(id, (Film) obj);
//                log.info("Фильм " + ((Film) obj).getName() + " обновлен");
//                //return new ResponseEntity<>(obj, HttpStatus.OK);
//            }
//        }
//        return ResponseEntity.ok(obj);

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
