package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.custom.LocalDateAdapter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.model.Film.testFilm;
import static ru.yandex.practicum.filmorate.model.User.testUser;

class FilmControllerTest {
    URI uri = URI.create("http://localhost:8080/films");

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Test
    void findAll() throws IOException, InterruptedException {
        Film filmGetAll = testFilm();
        filmGetAll.setId(998);
        String filmSerialized = gson.toJson(filmGetAll);
        HttpResponse<String> response = Client.http("POST", uri, filmSerialized);
        response = Client.http("GET", uri, null);
        assertTrue(response.statusCode() == 200, "Список фильмов не поступил");
    }

    @Test
    void update() throws IOException, InterruptedException {
        Client.http("DELETE", uri, null);
        Film filmCreate = testFilm();
        String filmSerialized = gson.toJson(filmCreate);
        HttpResponse<String> response = Client.http("POST", uri, filmSerialized);
        int id = gson.fromJson(response.body(), Film.class).getId();

        Film filmUpdate = testFilm();
        filmUpdate.setId(id);
        filmSerialized = gson.toJson(filmUpdate);
        response = Client.http("PUT", uri, filmSerialized);
        assertEquals(200, response.statusCode(), "Обновление фильма прошло неудачно");

        Client.http("DELETE", uri, null);
        User filmUpdateUnknown = testUser();
        filmUpdateUnknown.setId(999);
        filmSerialized = gson.toJson(filmUpdateUnknown);
        response = Client.http("PUT", uri, filmSerialized);
        assertEquals(500, response.statusCode(), "Фильм ошибочно включен в список пользователей");
    }

    @Test
    void create() throws IOException, InterruptedException {
        Film filmCreate = testFilm();
        Client.http("DELETE", uri, null);
        String filmSerialized = gson.toJson(filmCreate);
        HttpResponse<String> response = Client.http("POST", uri, filmSerialized);
        assertTrue(response.statusCode() == 200, "Фильм не загрузился");

        Film filmFallName = testFilm();
        filmFallName.setName("");
        Client.http("DELETE", uri, null);
        filmSerialized = gson.toJson(filmFallName);
        response = Client.http("POST", uri, filmSerialized);
        assertTrue(response.statusCode() == 500,"Фильм ошибочно загрузился с "
                + " неверным именем");

        Film filmFallDescription = testFilm();
        filmFallDescription.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль."
              + " Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги,"
              +  " а именно 20 миллионов. о Куглов, который за время «своего отсутствия»,"
              +  " стал кандидатом Коломбани.");
        Client.http("DELETE", uri, null);
        filmSerialized = gson.toJson(filmFallDescription);
        response = Client.http("POST", uri, filmSerialized);
        assertTrue(response.statusCode() == 500,"Фильм ошибочно загрузился с "
                + " длинной описания больше 200 символов");

        Film filmFallReleaseDate = testFilm();
        filmFallReleaseDate.setReleaseDate(LocalDate.of(1890, 3,25));
        Client.http("DELETE", uri, null);
        filmSerialized = gson.toJson(filmFallReleaseDate);
        response = Client.http("POST", uri, filmSerialized);
        assertTrue(response.statusCode() == 500,"Фильм ошибочно "
                + "загрузился с неверной датой релиза");

        Film filmFallDuration = testFilm();
        filmFallDuration.setDuration(-200);
        Client.http("DELETE", uri, null);
        filmSerialized = gson.toJson(filmFallDuration);
        response = Client.http("POST", uri, filmSerialized);
        assertTrue(response.statusCode() == 500,"Фильм ошибочно загрузился с "
                + " неверной продолжительностью");

    }

}