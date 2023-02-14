package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    UserStorage userStorage = new InMemoryUserStorage();

    @Test
    void getAll() {
        userStorage.deleteAll();
        User user = new User().testUser();
        userStorage.create(user);
        assertEquals(1, userStorage.getAll().size(), "Длина списка пользователей не  равна 1");
    }

    @Test
    void getById() {
        userStorage.deleteAll();
        User userGetById = new User().testUser();
        String nameUserGetById = userGetById.getName();
        userStorage.create(userGetById);
        Long id2 = userStorage.getAll().stream().map(e -> e.getId()).findFirst().get();

        User userOther = new User().testUser();
        userStorage.create(userOther);

        String nameUserFromStorage = userStorage.getById(id2).getName();
        assertEquals(nameUserGetById, nameUserFromStorage, "Ошибка вызова пользователя по id");

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
        userStorage.create(userCreate);
        Long id = userStorage.getAll().stream().map(e -> e.getId()).findFirst().get();

        User userUpdate = new User().testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        userStorage.update(userUpdate);
        ArrayList<User> users = (ArrayList<User>) userStorage.getAll().stream().collect(Collectors.toList());
        assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно ");
    }

    @Test
    void deleteAll() {
        User user1 = new User().testUser();
        userStorage.create(user1);
        User user2 = new User().testUser();
        userStorage.create(user2);

        userStorage.deleteAll();
        assertEquals(0, userStorage.getAll().size(), "Удаление всех пользователей прошло неудачно ");
    }
}