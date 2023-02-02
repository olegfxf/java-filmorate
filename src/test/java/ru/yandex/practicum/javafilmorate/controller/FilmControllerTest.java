package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.Manager;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    Manager<Film> manager = new Manager<>();

    @Test
    void create() {

        manager.deleteAll();
        Film filmCreate = new Film().testFilm();
        try {
            manager.create(filmCreate);
        }catch (Exception e){
            assertEquals(1, manager.findAll().size(), "Ошибка добавления фильма");
        }


        manager.deleteAll();
        Film filmWithEmptyName = new Film().testFilm();
        filmWithEmptyName.setName(null);
        try {
            manager.create(filmWithEmptyName);
        }catch (Exception e){
            assertEquals(0, manager.findAll().size(),"Фильм без названия внесен в список фильмов");
        }


        manager.deleteAll();
        Film filmFailDescription = new Film().testFilm();
        filmFailDescription.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. "
             +   "Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. "
             + "о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
        try {
            manager.create(filmFailDescription);
        }catch (Exception e){
            assertEquals(0, manager.findAll().size(),"В список фильмов ошибочно вставлен фильм"
                    + " с описанием боле 200 символов");
        }















    }

    @Test
    void update() {

    }
}