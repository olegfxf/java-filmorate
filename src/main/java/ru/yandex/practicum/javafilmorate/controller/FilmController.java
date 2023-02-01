package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;


@RestController
@Slf4j
public class FilmController extends Controller<Film>{
//
//    @PostMapping("/films")
//    public Film create(@Valid @RequestBody final Film film) throws ru.yandex.practicum.javafilmorate.exception.ValidationException {
//        log.info("Creating film {}", film);
//        return super.create(film);
//    }
//
//// TODO методы контроллера
//
//    void validation(Film data){
//        if(data.getName() == null || data.getName().isEmpty()){
//            throw new ValidationException("Film name invalid");
//        }
//
//// TODO валидация
//    }
}

