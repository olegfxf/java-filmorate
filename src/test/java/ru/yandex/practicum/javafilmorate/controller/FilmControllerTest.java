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
        manager.findAll().stream().forEach(e-> System.out.println(e.getName()));


//        manager.deleteAll();
//        Film filmWithEmptyName = new Film().testFilm();
//        filmWithEmptyName.setName(null);
//        try {
//            manager.create(filmWithEmptyName);
//        }catch (Exception e){
//            assertEquals(0, manager.findAll().size(),"Фильм без названия внесен в список фильмов");
//        }











    }

    @Test
    void update() {

    }
}