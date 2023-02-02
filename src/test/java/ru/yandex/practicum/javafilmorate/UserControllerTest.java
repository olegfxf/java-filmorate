package ru.yandex.practicum.javafilmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.controller.UserAndFilmController;
import ru.yandex.practicum.javafilmorate.controller.UserController;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;


class UserControllerTest {
    UserAndFilmController userAndFilmController = new UserController();



    @Test
    void findAll()  {
        userAndFilmController.deleteAll();
        User userGetAll = new User().testUser();
        userGetAll.setId(998L);
        try {
            userAndFilmController.create(userGetAll);
        }catch (Exception e) {
            assertEquals(1, userAndFilmController.findAll().size(), "Длина списка пользователей не  равна 1");
        }
    }


    @Test
    void update() {

        userAndFilmController.deleteAll();
        User userCreate = new User().testUser();
        Long id = userCreate.getId();
        try {
            userAndFilmController.create(userCreate);
        }catch (Exception e){}

        User userUpdate = new User().testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        try {
            userAndFilmController.update(userUpdate);
        }catch (Exception e) {
            ArrayList<User> users = (ArrayList<User>) userAndFilmController.findAll().stream().collect(Collectors.toList());
            assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно");
        }

        userAndFilmController.deleteAll();
        User userUpdateUnknown = new User().testUser();
        userUpdateUnknown.setId(999L);
        try {
            userAndFilmController.update(userUpdateName);
        }catch (Exception e) {
            assertEquals(0, userAndFilmController.findAll().size(), "Пользователь ошибочно включен в список пользователей");
        }
    }

    @Test
    void create() {

        userAndFilmController.deleteAll();
        User userCreate = new User().testUser();
        try {
            userAndFilmController.create(userCreate);
        } catch (Exception e) {
            assertEquals(1, userAndFilmController.findAll().size(), "Пользователь не создался");
        }

        User userFallLogin = new User().testUser();
        userFallLogin.setLogin("dolore ullamco");
        try {
            userAndFilmController.create(userFallLogin);
        } catch (Exception e) {
            assertEquals(1, userAndFilmController.findAll().size(), "Пост ошибочно загрузился"
                    + " с неверным логином");
        }

        userAndFilmController.deleteAll();
        User userFallEmail = new User().testUser();
        userFallEmail.setEmail("mail.ru");
        try {
            userAndFilmController.create(userFallEmail);
        } catch (Exception e) {
            assertEquals(0, userAndFilmController.findAll().size(), "Пост ошибочно загрузился с "
                    + " неверным email");
        }

        userAndFilmController.deleteAll();
        User userFallBirthday = new User().testUser();
        userFallBirthday.setBirthday(LocalDate.of(2446, 8, 20));
        try {
            userAndFilmController.create(userFallBirthday);
        } catch (Exception e) {
            assertEquals(0, userAndFilmController.findAll().size(), "Пост ошибочно "
                    + "загрузился с неверной датой рождения");
        }

        userAndFilmController.deleteAll();
        User userWithEmptyName = new User().testUser();
        userWithEmptyName.setName(null);
        try {
            userAndFilmController.create(userWithEmptyName);
        } catch (Exception e) {
            assertEquals(0, userAndFilmController.findAll().size(), "Пользователь c"
                    + " незаполненной графой 'имя пользователя'"
                    + " ошибочно включен в список пользователей");
        }
    }

}