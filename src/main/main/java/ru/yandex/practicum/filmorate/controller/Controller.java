package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;


@RequestMapping(value = "/users")
@RestController
@Slf4j
public abstract class Controller<E> {
    protected final HashSet<E> objs = new HashSet<>();

    @GetMapping
    public HashSet<E> findAll() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return objs;
    }


    @PutMapping
    public E update(@RequestBody E obj) throws UserUpdateUnknown,
            FilmFailReleaseDate, FilmWithEmptyName, FilmFailDurationNegative,
            FilmEmptyName, FilmUpdateUnknown {

        return null;
    }


    @PostMapping
    public E create(@RequestBody E obj) throws UserUpdateUnknown, UserCreateFailLogin,
            CreateUserWithEmptyName, UserCreateFailEmail, UserCreateFailBirthday,
            UserAlreadyExist, InvalidEmailException, FilmFailReleaseDate, FilmWithEmptyName,
            FilmFailDurationNegative, FilmEmptyName {
        System.out.println(obj + "  >>>>>");
        boolean isCreateUser = false;
        boolean isCreateFilm = false;

        if (obj instanceof User) {
            UserExceptionCreate createUser = new UserExceptionCreate();
            isCreateUser = createUser.update((HashSet<User>) objs, (User) obj);
        } else if (obj instanceof Film) {
            FilmExceptionCreate filmExceptionCreate = new FilmExceptionCreate();
            isCreateFilm = filmExceptionCreate.create((HashSet<Film>) objs, (Film) obj);
        }
//        users.stream().forEach(e -> System.out.println(e.getEmail()));
//        System.out.println();

        if (isCreateUser || isCreateFilm)
            objs.add(obj);

        return obj;
    }

    @DeleteMapping
    public void deleteAll() {
        objs.clear();
    }


}
