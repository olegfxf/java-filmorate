package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping(value = "/users")
@RestController
@Slf4j
public abstract class Controller<E> {
    private final HashMap<Integer, E> objs = new HashMap<>();
    //private final HashMap<Integer, E> objs1 = new HashMap<>();

    @GetMapping
    public List<E> findAll() {
        log.info("Выполнен запрос на вывод всех пользователей");

        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        objs.values().stream().forEach(e-> System.out.println(e.toString()));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");

        return objs.values().stream().collect(Collectors.toList());
    }


    @PutMapping
    public E update(@RequestBody E obj) throws UserUpdateUnknown, FilmFailReleaseDate, FilmWithEmptyName, FilmFailDurationNegative, FilmEmptyName, FilmUpdateUnknown {

//        users.stream().forEach(e -> System.out.println(e.getEmail()));
//        System.out.println();
        boolean isUpdateUser = false;
        boolean isUpdateFilm = false;

        if (obj instanceof User) {
            UserExceptionUpdate updateUser = new UserExceptionUpdate();
            isUpdateUser = updateUser.update((HashMap<Integer, User>) objs, (User) obj);
            if (isUpdateUser) {
                Integer id = ((User) obj).getId();
                ((HashMap<Integer, User>) objs).remove(id);
                ((HashMap<Integer, User>) objs).put(id, (User) obj);
                log.info("Пользователь " + ((User) obj).getName() + " обновлен");
                return obj;

//                for (User user1 : (HashSet<User>) objs) {
//                    if (user1.getId() == ((User) obj).getId()) {
//                        objs.remove(user1);
//                        objs.add(obj);
//                        log.info("Пользователь " + ((User) obj).getName() + " обновлен");
//                        return obj;
//                    }
//                }
            }
        } else if (obj instanceof Film) {
            FilmExceptionUpdate filmExceptionUpdate = new FilmExceptionUpdate();
            isUpdateFilm = filmExceptionUpdate.update((HashMap<Integer, Film>) objs, (Film) obj);
            if (isUpdateFilm) {
                Integer id = ((Film) obj).getId();
                ((HashMap<Integer, Film>) objs).remove(id);
                ((HashMap<Integer, Film>) objs).put(id, (Film) obj);
                log.info("Фильм " + ((Film)obj).getName() + " обновлен");
                return obj;
//                for (Film film1 : (HashSet<Film>)objs){
//                    if (film1.getId() == ((Film)obj).getId()) {
//                        objs.remove(film1);
//                        objs.add(obj);
//                        log.info("Фильм " + ((Film)obj).getName() + " обновлен");
//                        return obj;
//                    }
//                }
            }
        }

        return obj;
    }

    Integer id;
    @PostMapping
    public E create(@RequestBody E obj) throws UserUpdateUnknown, UserCreateFailLogin,
            CreateUserWithEmptyName, UserCreateFailEmail, UserCreateFailBirthday,
            UserAlreadyExist, InvalidEmailException, FilmFailReleaseDate, FilmWithEmptyName,
            FilmFailDurationNegative, FilmEmptyName {
        System.out.println(obj + "  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        boolean isCreateUser = false;
        boolean isCreateFilm = false;

        if (obj instanceof User) {
            UserExceptionCreate userExceptionCreate = new UserExceptionCreate();
            isCreateUser = userExceptionCreate.create((HashMap<Integer, User>) objs, (User) obj);
            id = ((User) obj).getId();
        } else if (obj instanceof Film) {
            FilmExceptionCreate filmExceptionCreate = new FilmExceptionCreate();
            isCreateFilm = filmExceptionCreate.create((HashMap<Integer, Film>) objs, (Film) obj);
            id = ((Film) obj).getId();
        }
//        users.stream().forEach(e -> System.out.println(e.getEmail()));
//        System.out.println();


        if (isCreateUser || isCreateFilm)
            objs.put(id, obj);

        return obj;
    }

    @DeleteMapping
    public void deleteAll() {
        objs.clear();
    }


}
