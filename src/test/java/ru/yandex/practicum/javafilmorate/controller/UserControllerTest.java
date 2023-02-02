package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;


class UserControllerTest {
    Manager<User> manager = new Manager<>();



    @Test
    void findAll()  {
        manager.deleteAll();
        User userGetAll = new User().testUser();
        userGetAll.setId(998L);
        try {
            manager.create(userGetAll);
        }catch (Exception e) {
            assertEquals(1, manager.findAll().size(), "Длина списка пользователей не  равна 1");
        }
    }


    @Test
    void update() {

        manager.deleteAll();
        User userCreate = new User().testUser();
        Long id = userCreate.getId();
        try {
            manager.create(userCreate);
        }catch (Exception e){}

        User userUpdate = new User().testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        ArrayList<User> users = (ArrayList<User>) manager.findAll().stream().collect(Collectors.toList());
        try {
            manager.update(userUpdate);
        }catch (Exception e) {
            assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно");
        }

        manager.deleteAll();
        User userUpdateUnknown = new User().testUser();
        userUpdateUnknown.setId(999L);
        try {
            manager.update(userUpdateName);
        }catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно включен в список пользователей");
        }
    }

    @Test
    void create() {

        manager.deleteAll();
        User userCreate = new User().testUser();
        try {
            manager.create(userCreate);
        } catch (Exception e) {
            assertEquals(1, manager.findAll().size(), "Пользователь не создался");
        }

        User userFallLogin = new User().testUser();
        userFallLogin.setLogin("dolore ullamco");
        try {
            manager.create(userFallLogin);
        } catch (Exception e) {
            assertEquals(1, manager.findAll().size(), "Пользователь ошибочно загрузился"
                    + " с неверным логином");
        }

        manager.deleteAll();
        User userFallEmail = new User().testUser();
        userFallEmail.setEmail("mail.ru");
        try {
            manager.create(userFallEmail);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно загрузился с "
                    + " неверным email");
        }

        manager.deleteAll();
        User userFallBirthday = new User().testUser();
        userFallBirthday.setBirthday(LocalDate.of(2446, 8, 20));
        try {
            manager.create(userFallBirthday);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь ошибочно "
                    + "загрузился с неверной датой рождения");
        }

        manager.deleteAll();
        User userWithEmptyName = new User().testUser();
        userWithEmptyName.setName(null);
        try {
            manager.create(userWithEmptyName);
        } catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Пользователь c"
                    + " незаполненной графой 'имя пользователя'"
                    + " ошибочно включен в список пользователей");
        }
    }

}