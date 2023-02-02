package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    Manager<Film> manager = new Manager<>();
    FilmController filmController =new FilmController();

    @Test
    void getAllFilm() throws ValidationException {
        manager.deleteAll();
        Film film = new Film().testFilm();
        try {
            filmController.create(film);
        } catch (Exception e) { }
        int size = filmController.getAllFilms().getBody().size();
        if (size != 1)
            throw new ValidationException("Некорректная длина списка фильмов");
    }

    @Test
    void create() {

        manager.deleteAll();
        Film filmCreate = new Film().testFilm();
        try {
            filmController.create(filmCreate);
        }catch (Exception e){
            assertEquals(1, manager.findAll().size(), "Ошибка добавления фильма");
        }


        manager.deleteAll();
        Film filmWithEmptyName = new Film().testFilm();
        filmWithEmptyName.setName(null);
        try {
            filmController.create(filmWithEmptyName);
        }catch (Exception e){
            assertEquals(0, manager.findAll().size(),"Фильм без названия внесен в список фильмов");
        }


        manager.deleteAll();
        Film filmFailDescription = new Film().testFilm();
        filmFailDescription.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. "
             +   "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. "
             + "о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
        try {
            filmController.create(filmFailDescription);
        }catch (Exception e){
            assertEquals(0, manager.findAll().size(),"В список фильмов ошибочно вставлен фильм"
                    + " с описанием боле 200 символов");
        }

        manager.deleteAll();
        Film filmReleaseDate = new Film().testFilm();
        filmReleaseDate.setReleaseDate(LocalDate.of(1890,3,25));
        try {
            filmController.create(filmReleaseDate);
        }catch (Exception e){
            assertEquals(0,manager.findAll().size(), "В список фильмов ошибочно вставлен фильм"
                    + " с датой выхода раньше 1890-03-25");
        }

        manager.deleteAll();
        Film filmDuration = new Film().testFilm();
        filmDuration.setDuration(0);
        try {
            filmController.create(filmDuration);
        }catch (Exception e){
            assertEquals(0,manager.findAll().size(),"В список фильмов ошибочно вставлен фильм"
                    + " с отрицательным временем");
        }
    }

    @Test
    void update() {

        manager.deleteAll();
        Film filmCreate = new Film().testFilm();
        Long id = filmCreate.getId();
        try {
            filmController.create(filmCreate);
        }catch (Exception e){}

        Film filmUpdate = new Film().testFilm();
        String userUpdateName = filmUpdate.getName();
        filmUpdate.setId(id);
        ArrayList<Film> films = (ArrayList<Film>) manager.findAll().stream().collect(Collectors.toList());
        try {
            filmController.update(filmUpdate);
        }catch (Exception e) {
            assertEquals(userUpdateName, films.get(0).getName(), "Обновление фильма прошло не успешно ");
        }

        manager.deleteAll();
        Film filmUpdateUnknown = new Film().testFilm();
        filmUpdateUnknown.setId(10000L);
        try {
            filmController.create(filmUpdateUnknown);
        }catch (Exception e) {
            assertEquals(0, manager.findAll().size(), "Не получилось обновить  фили с дефлектором");
        }
    }

}