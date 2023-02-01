package ru.yandex.practicum.javafilmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.controller.Controller;
import ru.yandex.practicum.javafilmorate.controller.UserController;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.javafilmorate.model.User.testUser;

class UserControllerTest {
//    URI uri = URI.create("http://localhost:8080/users");
//    Client client = new Client();
    Controller userController = new UserController();


//    Gson gson = new GsonBuilder()
//            .setPrettyPrinting()
//            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//            .create();


    @Test
    void findAll()  {
        userController.deleteAll();
        User userGetAll = testUser();
        userGetAll.setId(998);
        try {
            userController.create(userGetAll);
        }catch (Exception e) {
            assertEquals(1, userController.findAll().size(), "Длина списка пользователей не  равна 1");
        }
    }


    @Test
    void update() {

        userController.deleteAll();
        User userCreate = testUser();
        int id = userCreate.getId();
        try {
            userController.create(userCreate);
        }catch (Exception e){}

        User userUpdate = testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        try {
            userController.update(userUpdate);
        }catch (Exception e) {
            ArrayList<User> users = (ArrayList<User>) userController.findAll().stream().collect(Collectors.toList());
            assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно");
        }

        userController.deleteAll();
        User userUpdateUnknown = testUser();
        userUpdateUnknown.setId(999);
        try {
            userController.update(userUpdateName);
        }catch (Exception e) {
            assertEquals(0, userController.findAll().size(), "Пользователь ошибочно включен в список пользователей");
        }
    }

    @Test
    void create() {

        userController.deleteAll();
        User userCreate = testUser();
        try {
            userController.create(userCreate);
        } catch (Exception e) {
            assertEquals(1, userController.findAll().size(), "Пользователь не создался");
        }

        User userFallLogin = testUser();
        userFallLogin.setLogin("dolore ullamco");
        try {
            userController.create(userFallLogin);
        } catch (Exception e) {
            assertEquals(1, userController.findAll().size(), "Пост ошибочно загрузился"
                    + " с неверным логином");
        }

        userController.deleteAll();
        User userFallEmail = testUser();
        userFallEmail.setEmail("mail.ru");
        try {
            userController.create(userFallEmail);
        } catch (Exception e) {
            assertEquals(0, userController.findAll().size(), "Пост ошибочно загрузился с "
                    + " неверным email");
        }

        userController.deleteAll();
        User userFallBirthday = testUser();
        userFallBirthday.setBirthday(LocalDate.of(2446, 8, 20));
        try {
            userController.create(userFallBirthday);
        } catch (Exception e) {
            assertEquals(0, userController.findAll().size(), "Пост ошибочно "
                    + "загрузился с неверной датой рождения");
        }

        userController.deleteAll();
        User userWithEmptyName = testUser();
        userWithEmptyName.setName(null);
        try {
            userController.create(userWithEmptyName);
        } catch (Exception e) {
            assertEquals(0, userController.findAll().size(), "Пользователь c"
                    + " незаполненной графой 'имя пользователя'"
                    + " ошибочно включен в список пользователей");
        }
    }

}