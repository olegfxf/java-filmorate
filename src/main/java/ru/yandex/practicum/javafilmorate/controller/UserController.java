package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController extends Controller<User> {
    @PostMapping
    public ResponseEntity<?> create(@RequestBody final User user) throws ValidationException {
        validation(user);
        HashMap<Long, User> users = super.getStorages();
        if (users.size() != 0) {
            for (Long idUser : users.keySet()) {
                if (idUser.equals(user.getId())) {
                    throw new ValidationException("Пользователь " + user.getBirthday()
                            + " уже существует");
                }
            }
        }

        log.info("Creating user {}", user);
        return super.create(user);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody final User user) throws ValidationException {
        validation(user);
        HashMap<Long, User> users = super.getStorages();
        for (Long idUser : users.keySet()) {
            if (idUser.equals(user.getId())) {
                log.info("Пользователь " + user.getName() + " обновлен");
                return super.update(user);
            }
        }
        throw new ValidationException("Пользователь " + user.getName() + " неизвестен");

    }


    void validation(User user) throws ValidationException {
        HashMap<Long, User> users = super.getStorages();

        if (user.getName() == null) {
            String userName = user.getLogin();
            user.setId(2L);
            user.setName(userName);
            log.info("Новое имя пользователя стало: " + userName);
        }

        if (user.getEmail().isEmpty())
            throw new ValidationException("Вы не ввели email");


        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            throw new ValidationException("Неправильный email");

        if (user.getName().isBlank())
            throw new ValidationException("Введите имя пользователя");

        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException("Неправильная дата рождения");

        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            throw new ValidationException("Логин содержит пробел");


    }
}
