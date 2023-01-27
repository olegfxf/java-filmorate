package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

@RequestMapping(value = "/users")
@RestController
@Slf4j
public class UserController {
    private final HashSet<User> users = new HashSet<>();

    @GetMapping
    public HashSet<User> findAll() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return users;
    }

    @PutMapping
    public User update(@RequestBody User user) throws UserUpdateUnknown {

        users.stream().forEach(e -> System.out.println(e.getEmail()));
        System.out.println();

        try {
            if (user.getEmail().isEmpty()) {
                throw new InvalidEmailExceptionOld("Вы не ввели email");
            }
        } catch (InvalidEmailExceptionOld exception) {
            System.out.println(exception.getMessage());
        }


        for (User user1 : users) {
            if (user1.getId() == user.getId()) {
                users.remove(user1);
                users.add(user);
                log.info("Пользователь " + user.getName() + " обновлен");
                return user;
            }
        }

        throw new UserUpdateUnknown("Пользователь " + user.getName() + " неизвестен");
    }


    @PostMapping
    public User create(@RequestBody User user) throws
            UserCreateFailEmail, CreateUserWithEmptyName, UserCreateFailBirthday,
            UserCreateFailLogin, UserAlreadyExist, InvalidEmailException {

        if (user.getName() == null) {
            String userName = user.getLogin();
            user.setName(userName);
            log.info("Новое имя пользователя стало: "+ userName);
        }

//        users.stream().forEach(e -> System.out.println(e.getEmail()));
//        System.out.println();

        if (user.getEmail().isEmpty()) //{
            throw new InvalidEmailException("Вы не ввели email");


        if (users.size() != 0) {
            for (User user1 : users) {
                if (user1.getId() == user.getId()) {
                    throw new UserAlreadyExist("Пользователь " + user.getBirthday() + " уже существует");
                }
            }
        }


        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            throw new UserCreateFailEmail("Неправильный email");

        if (user.getName().isBlank())
            throw new CreateUserWithEmptyName("Введите имя пользователя");

        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new UserCreateFailBirthday("Неправильная дата рождения");

        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            throw new UserCreateFailLogin("Логин содержит пробел");

        users.add(user);
        log.info("Пользователь с именем " + user.getName() + " успешно добавлен");
        return user;
    }


}
