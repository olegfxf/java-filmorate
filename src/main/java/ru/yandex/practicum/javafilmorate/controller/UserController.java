package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
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
    public User create(@Valid @RequestBody final User user) throws ValidationException1 {
        validation(user);
        return inMemoryUserStorage.create(user);
    }

    @PutMapping
    @ResponseBody
    public User update(@Valid @RequestBody final User user) throws ValidationException1 {
        validation(user);
        return inMemoryUserStorage.update(user);

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
