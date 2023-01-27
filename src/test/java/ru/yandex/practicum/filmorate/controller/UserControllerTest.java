package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
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

class UserControllerTest {
    URI uri = URI.create("http://localhost:8080/users");


    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    User userCreate;
    User userFallLogin;
    User userFallEmail;
    User userFallBirthday;
    User userUpdate;
    User userGetAll;
    User userUpdateUnknown;
    User userWithEmptyName;



    @BeforeEach
    void beforeEach(){
        userCreate = gson.fromJson("{\n" +
                "  \"login\": \"dolore\",\n" +
                "  \"name\": \"Nick Name\",\n" +
                "  \"email\": \"mail@mail.ru\",\n" +
                "  \"birthday\": \"1946-08-20\"\n" +
                "}", User.class);
        userFallLogin = gson.fromJson("{\n" +
                "  \"login\": \"dolore ullamco\",\n" +
                "  \"email\": \"yandex@mail.ru\",\n" +
                "  \"birthday\": \"1996-08-20\"\n" +
                "}", User.class);
        userFallEmail = gson.fromJson("{\n" +
                "  \"login\": \"dolore ullamco\",\n" +
                "  \"name\": \"\",\n" +
                "  \"email\": \"mail.ru\",\n" +
                "  \"birthday\": \"1980-08-20\"\n" +
                "}", User.class);
        userFallBirthday = gson.fromJson("{\n" +
                "  \"login\": \"dolore\",\n" +
                "  \"name\": \"xxxx\",\n" +
                "  \"email\": \"test@mail.ru\",\n" +
                "  \"birthday\": \"2446-08-20\"\n" +
                "}", User.class);
        userUpdate = gson.fromJson("{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"id\": 1,\n" +
                "  \"email\": \"mail@yandex.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}", User.class);
        userGetAll = gson.fromJson("{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"estadipisicing\",\n" +
                "  \"id\": 998,\n" +
                "  \"email\": \"mail@mail.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}", User.class);
        userUpdateUnknown = gson.fromJson("{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"id\": 9999,\n" +
                "  \"email\": \"mail@yandex.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}", User.class);
        userWithEmptyName = gson.fromJson("{\n" +
                "  \"login\": \"common\",\n" +
                "  \"email\": \"friend@common.ru\",\n" +
                "  \"birthday\": \"2000-08-20\"\n" +
                "}", User.class);

    }

    @Test
    void findAll() throws IOException, InterruptedException {

        User userGetAll = new User();
        userGetAll.setLogin("doloreUpdate");
        userGetAll.setName("estadipisicing");
        userGetAll.setId(998);
        userGetAll.setEmail("mail@mail.ru");
        userGetAll.setBirthday(LocalDate.of(1976,9,20));





        String postSerialized = gson.toJson(userGetAll);
        HttpResponse<String> response = Client.http("POST", uri, postSerialized);

        response = Client.http("GET", uri, null);
        assertTrue(response.statusCode() == 200, "Список пользователей не поступил");
    }


    @Test
    void update() throws IOException, InterruptedException {
        String postSerialized = gson.toJson(userUpdate);
        HttpResponse<String> response = Client.http("PUT", uri, postSerialized);
        assertEquals(200, response.statusCode(), "Обновление пользователя прошло неудачно");

        postSerialized = gson.toJson(userUpdateUnknown);
        response = Client.http("PUT", uri, postSerialized);
        assertEquals(500, response.statusCode(), "Пользователь ошибочно включен в список пользователей");
    }

    @Test
    void create() throws IOException, InterruptedException {

        String postSerialized = gson.toJson(userCreate);
        HttpResponse<String> response = Client.http("POST", uri, postSerialized);
        assertTrue(response.statusCode() == 200, "Пост не загрузился");

        postSerialized = gson.toJson(userFallLogin);
        response = Client.http("POST", uri, postSerialized);
        assertTrue(response.statusCode() == 500,"Пост ошибочно загрузился"
                                                                                  + " с неверным логином");

        postSerialized = gson.toJson(userFallEmail);
        response = Client.http("POST", uri, postSerialized);
        assertTrue(response.statusCode() == 400,"Пост ошибочно загрузился с "
                                                                              + " неверным email");

        postSerialized = gson.toJson(userFallBirthday);
        response = Client.http("POST", uri, postSerialized);
        assertTrue(response.statusCode() == 400,"Пост ошибочно загрузился с "
                                                                                        + " неверной датой рождения");

        postSerialized = gson.toJson(userWithEmptyName);
        response = Client.http("PUT", uri, postSerialized);
        assertEquals(500, response.statusCode(), "Пользователь c незаполненной графой 'имя пользователя'"
               + " ошибочно включен в список пользователей");
    }


}