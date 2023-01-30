package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.custom.LocalDateAdapter;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.yandex.practicum.filmorate.model.User.testUser;

class UserControllerTest {
    URI uri = URI.create("http://localhost:8080/users");


    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    @Test
    void findAll() throws IOException, InterruptedException {
        User userGetAll = testUser();
        userGetAll.setId(998);
        String userSerialized = gson.toJson(userGetAll);
        HttpResponse<String> response = Client.http("POST", uri, userSerialized);
        response = Client.http("GET", uri, null);
        assertTrue(response.statusCode() == 200, "Список пользователей не поступил");
    }


    @Test
    void update() throws IOException, InterruptedException {

        Client.http("DELETE", uri, null);
        User userCreate = testUser();
        String userSerialized = gson.toJson(userCreate);
        HttpResponse<String> response = Client.http("POST", uri, userSerialized);
        int id = gson.fromJson(response.body(), User.class).getId();

        User userUpdate = testUser();
        userUpdate.setId(id);
        //Pair<User> pair = new Pair<>(userCreate, userUpdate);
        userSerialized = gson.toJson(userUpdate);
        response = Client.http("PUT", uri, userSerialized);
        assertEquals(200, response.statusCode(), "Обновление пользователя прошло неудачно");

        Client.http("DELETE", uri, null);
        User userUpdateUnknown = testUser();
        userUpdateUnknown.setId(999);
        userSerialized = gson.toJson(userUpdateUnknown);
        response = Client.http("PUT", uri, userSerialized);
        assertEquals(500, response.statusCode(), "Пользователь ошибочно включен в список пользователей");

    }

    @Test
    void create() throws IOException, InterruptedException {
        User userCreate = testUser();
        Client.http("DELETE", uri, null);
        String userSerialized = gson.toJson(userCreate);
        HttpResponse<String> response = Client.http("POST", uri, userSerialized);
        assertTrue(response.statusCode() == 200, "Пост не загрузился");


        User userFallLogin = testUser();
        userFallLogin.setLogin("dolore ullamco");
        userSerialized = gson.toJson(userFallLogin);
        response = Client.http("POST", uri, userSerialized);
        assertTrue(response.statusCode() == 500,"Пост ошибочно загрузился"
                                                           + " с неверным логином");

        User userFallEmail = testUser();
        userFallEmail.setEmail("mail.ru");
        Client.http("DELETE", uri, null);
        userSerialized = gson.toJson(userFallEmail);
        response = Client.http("POST", uri, userSerialized);
        assertTrue(response.statusCode() == 500,"Пост ошибочно загрузился с "
                                                           + " неверным email");

        User userFallBirthday = testUser();
        userFallBirthday.setBirthday(LocalDate.of(2446, 8,20));
        Client.http("DELETE", uri, null);
        userSerialized = gson.toJson(userFallBirthday);
        response = Client.http("POST", uri, userSerialized);

        assertTrue(response.statusCode() == 500,"Пост ошибочно "
                + "загрузился с неверной датой рождения"
                                                                                        + " неверной датой рождения");

        User userWithEmptyName = testUser();
        userWithEmptyName.setName(null);
        Client.http("DELETE", uri, null);
        userSerialized = gson.toJson(userWithEmptyName);
        response = Client.http("POST", uri, userSerialized);
        assertEquals(200, response.statusCode(), "Пользователь c"
               + " незаполненной графой 'имя пользователя'"
               + " ошибочно включен в список пользователей");
        Client.http("DELETE", uri, null);
    }

}