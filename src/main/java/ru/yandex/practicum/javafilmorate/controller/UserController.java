package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserController extends Controller<User> {
//    @PostMapping("/users")
//    public User create(@Valid @RequestBody final User user) throws ru.yandex.practicum.javafilmorate.exception.ValidationException {
//        log.info("Creating user {}", user);
//        return super.create(user);
//    }
//
//// TODO методы контроллера
//
//
//
//    // TODO валидация
//    void validationCreate(HashMap<Integer, User> users, User user) throws ValidationException {
////        if(user.getName() == null || user.getName().isEmpty()){
////            throw new ValidationException("User name invalid");
////        }
////
//
//        if (user.getName() == null) {
//            String userName = user.getLogin();
//            user.setName(userName);
//            log.info("Новое имя пользователя стало: " + userName);
//        }
//
//
//        if (user.getEmail().isEmpty())
//            throw new ValidationException("Вы не ввели email");
//
//        if (users.size() != 0) {
//            for (Integer idUser : users.keySet()) {
//                if (idUser.equals(user.getId())) {
//                    throw new ValidationException("Пользователь " + user.getBirthday()
//                            + " уже существует");
//                }
//            }
//        }
//
//        if (user.getEmail().isEmpty() || !user.getEmail().contains("@"))
//            throw new ValidationException("Неправильный email");
//
//        if (user.getName().isBlank())
//            throw new ValidationException("Введите имя пользователя");
//
//        if (user.getBirthday().isAfter(LocalDate.now()))
//            throw new ValidationException("Неправильная дата рождения");
//
//        if (user.getLogin().isEmpty() || user.getLogin().contains(" "))
//            throw new ValidationException("Логин содержит пробел");
//
//
//        log.info("Пользователь с именем " + user.getName() + " успешно добавлен");
//    }
//
//
//    // TODO валидация

}
