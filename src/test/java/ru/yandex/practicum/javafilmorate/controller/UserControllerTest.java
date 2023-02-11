package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    InMemoryUserStorage userStorage = new InMemoryUserStorage();

    @Test
    void getAll() {
        userStorage.deleteAll();
        User user = new User().testUser();
        userStorage.create(user);
        assertEquals(1, userStorage.getAll().size(), "Длина списка пользователей не  равна 1");
    }

    @Test
    void create() {
        userStorage.deleteAll();
        User user = new User().testUser();
        userStorage.create(user);
        assertEquals(1, userStorage.getAll().size(), "Пользователь ошибочно загрузился" + " с неверным логином");
    }

    @Test
    void update() {
        userStorage.deleteAll();
        User userCreate = new User().testUser();
        Long id = userCreate.getId();
        userStorage.create(userCreate);

        User userUpdate = new User().testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        userStorage.update(userUpdate);
        ArrayList<User> users = (ArrayList<User>) userStorage.getAll().stream().collect(Collectors.toList());
        assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно ");
    }
}