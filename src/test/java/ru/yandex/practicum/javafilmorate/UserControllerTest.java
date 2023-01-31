package ru.yandex.practicum.javafilmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.custom.LocalDateAdapter;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.util.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.yandex.practicum.javafilmorate.model.User.testUser;

class UserControllerTest {
    URI uri = URI.create("http://localhost:8080/users");
    Client client = new Client();


    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    @Test
    void findAll() throws IOException, InterruptedException {
        User userGetAll = testUser();
        userGetAll.setId(998);
        String userSerialized = gson.toJson(userGetAll);
        client.http("POST", uri, userSerialized);
        HttpResponse<String> response = client.http("GET", uri, null);
        assertTrue(response.statusCode() == 200, "Список пользователей не поступил");
    }


    @Test
    void update() throws IOException, InterruptedException {

//        client.http("DELETE", uri, null);
        User userCreate = testUser();
        String userSerialized = gson.toJson(userCreate);
        HttpResponse<String> response = client.http("POST", uri, userSerialized);
        int id = gson.fromJson(response.body(), User.class).getId();

        User userUpdate = testUser();
        userUpdate.setId(id);
        userSerialized = gson.toJson(userUpdate);
        response = client.http("PUT", uri, userSerialized);
        assertEquals(200, response.statusCode(), "Обновление пользователя прошло неудачно");

        client.http("DELETE", uri, null);
        User userUpdateUnknown = testUser();
        userUpdateUnknown.setId(999);
        userSerialized = gson.toJson(userUpdateUnknown);
        response = client.http("PUT", uri, userSerialized);
        assertEquals(500, response.statusCode(), "Пользователь ошибочно включен в список пользователей");

    }

    @Test
    void create() throws IOException, InterruptedException {
        User userCreate = testUser();
//        client.http("DELETE", uri, null);
        String userSerialized = gson.toJson(userCreate);
        HttpResponse<String> response = client.http("POST", uri, userSerialized);
        assertTrue(response.statusCode() == 200, "Пост не загрузился");


        User userFallLogin = testUser();
        userFallLogin.setLogin("dolore ullamco");
        userSerialized = gson.toJson(userFallLogin);
        response = client.http("POST", uri, userSerialized);
        assertTrue(response.statusCode() == 500,"Пост ошибочно загрузился"
                                                           + " с неверным логином");

        User userFallEmail = testUser();
        userFallEmail.setEmail("mail.ru");
        client.http("DELETE", uri, null);
        userSerialized = gson.toJson(userFallEmail);
        response = client.http("POST", uri, userSerialized);
        assertTrue(response.statusCode() == 500,"Пост ошибочно загрузился с "
                                                           + " неверным email");

        User userFallBirthday = testUser();
        userFallBirthday.setBirthday(LocalDate.of(2446, 8,20));
        client.http("DELETE", uri, null);
        userSerialized = gson.toJson(userFallBirthday);
        response = client.http("POST", uri, userSerialized);

        assertTrue(response.statusCode() == 500,"Пост ошибочно "
                + "загрузился с неверной датой рождения"
                                                                                        + " неверной датой рождения");

        User userWithEmptyName = testUser();
        userWithEmptyName.setName(null);
        client.http("DELETE", uri, null);
        userSerialized = gson.toJson(userWithEmptyName);
        response = client.http("POST", uri, userSerialized);
        assertEquals(200, response.statusCode(), "Пользователь c"
               + " незаполненной графой 'имя пользователя'"
               + " ошибочно включен в список пользователей");
        client.http("DELETE", uri, null);
    }

}