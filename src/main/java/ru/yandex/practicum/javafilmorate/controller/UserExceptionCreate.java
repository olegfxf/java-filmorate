package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

@Slf4j
public class UserExceptionCreate {
    public boolean create(HashMap<Integer, User> users, User user) throws InvalidEmailException, UserCreateFailLogin,
            UserCreateFailBirthday, CreateUserWithEmptyName, UserCreateFailEmail, UserAlreadyExist {

        if (user.getName() == null) {
            String userName = user.getLogin();
            user.setName(userName);
            log.info("Новое имя пользователя стало: " + userName);
        }


        if (user.getEmail().isEmpty())
            throw new InvalidEmailException("Вы не ввели email");

        if (users.size() != 0) {
            for (Integer idUser : users.keySet()) {
                if (idUser.equals(user.getId())) {
                    throw new UserAlreadyExist("Пользователь " + user.getBirthday()
                            + " уже существует");
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


        log.info("Пользователь с именем " + user.getName() + " успешно добавлен");
        return true;
    }

}
