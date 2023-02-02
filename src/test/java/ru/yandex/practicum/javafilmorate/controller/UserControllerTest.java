package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;


class UserControllerTest {
    Manager<User> manager = new Manager<>();
    UserController userController = new UserController();


    @Test
    void getAllFilm() throws ValidationException {
        manager.deleteAll();
        User user = new User().testUser();
        try {
            userController.create(user);
        } catch (Exception e) { }
        int size = userController.getAllFilms().getBody().size();
        if (size != 1)
            throw new ValidationException("Длина списка пользователей не  равна 1");
    }

    @Test
    void create() {

        manager.deleteAll();
        User userCreate = new User().testUser();
        try {
            userController.create(userCreate);
        } catch (Exception e) {
            assertEquals(1, manager.findAll().size(), "Пользователь не создался");
        }

        manager.deleteAll();
        User userFailLogin = new User().testUser();
        userFailLogin.setLogin("dolore ullamco");
        try {
            userController.create(userFailLogin);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно загрузился"
                    + " с неверным логином");
        }

        manager.deleteAll();
        User userFailEmail = new User().testUser();
        userFailEmail.setEmail("mail.ru");
        try {
            userController.create(userFailEmail);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно загрузился с "
                    + " неверным email");
        }

        manager.deleteAll();
        User userFailBirthday = new User().testUser();
        userFailBirthday.setBirthday(LocalDate.of(2446, 8, 20));
        try {
            userController.create(userFailBirthday);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно "
                    + "загрузился с неверной датой рождения");
        }

        manager.deleteAll();
        User userWithEmptyName = new User().testUser();
        userWithEmptyName.setName(null);
        try {
            userController.create(userWithEmptyName);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь c"
                    + " незаполненной графой 'имя пользователя'"
                    + " ошибочно включен в список пользователей");
        }
    }

    @Test
    void update() {

        manager.deleteAll();
        User userCreate = new User().testUser();
        Long id = userCreate.getId();
        try {
            userController.create(userCreate);
        }catch (Exception e){}

        User userUpdate = new User().testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        ArrayList<User> users = (ArrayList<User>) manager.findAll().stream().collect(Collectors.toList());
        try {
            userController.update(userUpdate);
        }catch (Exception e) {
            assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно");
        }

        manager.deleteAll();
        User userUpdateUnknown = new User().testUser();
        userUpdateUnknown.setId(999L);
        try {
            userController.update(userUpdateUnknown);
        }catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно включен в список пользователей");
        }
    }
}