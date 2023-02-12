package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
@RequestMapping("/users")
@RestController
@Slf4j
public class UserController {
    InMemoryUserStorage inMemoryUserStorage;// = new InMemoryUserStorage();
    UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }


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


    @PutMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        System.out.println(" qqqq " + friendId + " ffff " + id);
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseBody
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public Set<Long> getALL(@PathVariable Long id) {
        return userService.getALL(id);
    }
    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public User commonFriend(@PathVariable Long id, @PathVariable Long otherId) {
        System.out.println("eeeeeeeeeeeeeeeee " + id + " wwwwwwwwwwww " + otherId);
        return (User) userService.commonFriend(id, otherId);
    }


    void validation(User user) throws ValidationException1 {
        if (user.getName() == null || user.getName().isBlank()) {
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
