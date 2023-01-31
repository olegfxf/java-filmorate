package ru.yandex.practicum.javafilmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.controller.Controller;
import ru.yandex.practicum.javafilmorate.controller.UserController;
import ru.yandex.practicum.javafilmorate.custom.LocalDateAdapter;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.util.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.yandex.practicum.javafilmorate.model.User.testUser;

class UserControllerTest {
    URI uri = URI.create("http://localhost:8080/users");
    Client client = new Client();
    Controller userController = new UserController();


    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    @Test
    void findAll() throws IOException, InterruptedException, FilmFailReleaseDate, UserCreateFailLogin, FilmWithEmptyName, CreateUserWithEmptyName, UserCreateFailEmail, FilmFailDurationNegative, UserCreateFailBirthday, UserAlreadyExist, FilmEmptyName, UserUpdateUnknown, InvalidEmailException {
        userController.deleteAll();
        User userGetAll = testUser();
        userGetAll.setId(998);
        userController.create(userGetAll);
        System.out.println(userController.findAll().size());
        assertEquals(1, userController.findAll().size(), "Длина списка пользователей не  равна 1");
    }


    @Test
    void update() throws FilmFailReleaseDate, UserCreateFailLogin, FilmWithEmptyName, CreateUserWithEmptyName,
            UserCreateFailEmail, FilmFailDurationNegative, UserCreateFailBirthday, UserAlreadyExist, FilmEmptyName,
            UserUpdateUnknown, InvalidEmailException, FilmUpdateUnknown {

        userController.deleteAll();
        User userCreate = testUser();
        int id = userCreate.getId();
        userController.create(userCreate);

        User userUpdate = testUser();
        String userUpdateName = userUpdate.getName();
        userUpdate.setId(id);
        userController.update(userUpdate);
        ArrayList<User> users = (ArrayList<User>) userController.findAll().stream().collect(Collectors.toList());
        System.out.println(users + "UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        assertEquals(userUpdateName, users.get(0).getName(), "Обновление пользователя прошло неудачно");

        userController.deleteAll();
        User userUpdateUnknown = testUser();
        userUpdateUnknown.setId(999);
        userController.update(userUpdateName);
        assertEquals(0, userController.findAll().size(), "Пользователь ошибочно включен в список пользователей");


    }

    @Test
    void create() throws IOException, InterruptedException, FilmFailReleaseDate, UserCreateFailLogin, FilmWithEmptyName, CreateUserWithEmptyName, UserCreateFailEmail, FilmFailDurationNegative, UserCreateFailBirthday, UserAlreadyExist, FilmEmptyName, UserUpdateUnknown, InvalidEmailException {
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