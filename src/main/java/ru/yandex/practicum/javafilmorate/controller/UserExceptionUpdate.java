package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.javafilmorate.exception.InvalidEmail;
import ru.yandex.practicum.javafilmorate.exception.UserUpdateUnknown;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;

@Slf4j
public class UserExceptionUpdate {
    public boolean update(HashMap<Integer, User> users, User user) throws UserUpdateUnknown {
        //users.stream().forEach(e -> System.out.println(e.getEmail()));
        //System.out.println();

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
                return true;
            }
        }

        throw new UserUpdateUnknown("Пользователь " + user.getName() + " неизвестен");
    }

}
