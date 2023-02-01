package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.InvalidEmail;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController extends Controller<User> {
    @PostMapping
    public User create(@Valid @RequestBody final User user) throws ValidationException {
        validationCreate(user);
        log.info("Creating user {}", user);
        return super.create(user);
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody final User user) throws ValidationException {
        validationUpdate(user);
        log.info("Update user {}", user);
        return super.update(user);
    }

// TODO методы контроллера

    void validationCreate(User user) throws ValidationException {
        HashMap<Integer, User> users = super.getObjs();

        if (user.getName() == null) {
            //deleteAll();
            String userName = user.getLogin();
            //user.setId(1);
            user.setName(userName);
            log.info("Новое имя пользователя стало: " + userName);
        }

        if (user.getEmail().isEmpty())
            throw new ValidationException("Вы не ввели email");

        if (users.size() != 0) {
            for (Integer idUser : users.keySet()) {
                if (idUser.equals(user.getId())) {
                    throw new ValidationException("Пользователь " + user.getBirthday()
                            + " уже существует");
                }
            }
        }

        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            throw new ValidationException("Неправильный email");

        if (user.getName().isBlank())
            throw new ValidationException("Введите имя пользователя");

        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException("Неправильная дата рождения");

        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            throw new ValidationException("Логин содержит пробел");

        log.info("Пользователь с именем " + user.getName() + " успешно добавлен");
    }


    void validationUpdate(User user) throws ValidationException {
        HashMap<Integer, User> users = super.getObjs();
        try {
            if (user.getEmail().isEmpty()) {
                throw new InvalidEmail("Вы не ввели email");
            }
        } catch (InvalidEmail exception) {
            System.out.println(exception.getMessage());
        }

        for (Integer idUser : users.keySet()) {
            if (idUser.equals(user.getId())) {
                log.info("Пользователь " + user.getName() + " обновлен");
                return;
            }
        }

        deleteAll();
        throw new ValidationException("Пользователь " + user.getName() + " неизвестен");
    }

    // TODO валидация
}
