package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController {
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @GetMapping
    @ResponseBody
    public List<User> getAll() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return inMemoryUserStorage.getAll();
    }


    @PostMapping
    @ResponseBody
    public User create(@RequestBody final User user) throws ValidationException1 {
        validation(user);
        if (inMemoryUserStorage.storages.size() != 0) {
            for (Long idUser : inMemoryUserStorage.storages.keySet()) {
                if (idUser.equals(user.getId())) {
                    throw new ValidationException1("Пользователь " + user.getBirthday()
                            + " уже существует");
                }
            }
        }

        log.info("Creating user {}", user);
        //return super.create(user);
        return inMemoryUserStorage.create(user);
    }

    @PutMapping
    @ResponseBody
    public User update(@RequestBody final User user) throws ValidationException1 {
        validation(user);
        HashMap<Long, User> users = inMemoryUserStorage.storages;
        for (Long idUser : users.keySet()) {
            if (idUser.equals(user.getId())) {
                log.info("Пользователь " + user.getName() + " обновлен");
                //return super.update(user);
                return inMemoryUserStorage.update(user);
            }
        }
        throw new ValidationException1("Пользователь " + user.getName() + " неизвестен");

    }


    void validation(User user) throws ValidationException1 {
        HashMap<Long, User> users = inMemoryUserStorage.storages;

        if (user.getName() == null) {
            String userName = user.getLogin();
            user.setId(2L);
            user.setName(userName);
            log.info("Новое имя пользователя стало: " + userName);
        }

        if (user.getEmail().isEmpty())
            throw new ValidationException1("Вы не ввели email");


        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            throw new ValidationException1("Неправильный email");

        if (user.getName().isBlank())
            throw new ValidationException1("Введите имя пользователя");

        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException1("Неправильная дата рождения");

        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            throw new ValidationException1("Логин содержит пробел");


    }
}
