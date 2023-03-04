package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException400;
import ru.yandex.practicum.javafilmorate.exception.ValidationException500;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Component
@RequestMapping("/users")
@RestController
@Slf4j
public class UserController {
    private UserStorage userStorage;
    private UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public List<User> getAll() {
        log.info("Выполнен запрос на вывод всех пользователей");
        return userStorage.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getById(@PathVariable Long id) {
        log.info("Выполнен запрос на вывод пользователя с id = " + id);
        return userStorage.getById(id);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public List<User> getAllFriends(@PathVariable Long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public List<User> commonFriend(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.commonFriend(id, otherId);
    }


    @PostMapping
    @ResponseBody
    public User create(@Valid @RequestBody final User user) throws ValidationException400 {
        validation(user);
        return userStorage.create(user);
    }


    @PutMapping
    @ResponseBody
    public User update(@Valid @RequestBody final User user) throws ValidationException500 {
        validation(user);
        return userStorage.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.addFriend(id, friendId);
    }


    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriend(id, friendId);
    }


    void validation(User user) throws ValidationException400 {
        if (user.getName() == null || user.getName().isBlank()) {
            String userName = user.getLogin();
            user.setName(userName);
            log.info("Новое имя пользователя стало: " + userName);
        }

        if (user.getEmail().isEmpty())
            throw new ValidationException400("Вы не ввели email");


        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
            throw new ValidationException400("Неправильный email");

        if (user.getName().isBlank())
            throw new ValidationException400("Введите имя пользователя");

        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException400("Неправильная дата рождения");

        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
            throw new ValidationException400("Логин содержит пробел");


    }
}
