package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@RequestMapping(value = "/users")
@RestController
public class UserController extends Controller<User> {

    @Override
    public User update(@RequestBody User user) throws UserUpdateUnknown {
        boolean isUpdateUser = false;

        UserExceptionUpdate updateUser = new UserExceptionUpdate();
        isUpdateUser = updateUser.update(objs, user);
        if (isUpdateUser)
            for (User user1 : objs) {
                if (user1.getId() ==  user.getId()) {
                    objs.remove(user1);
                    objs.add(user);
                    log.info("Пользователь " + user.getName() + " обновлен");
                    return user;
                }
            }
        return null;
    }

}
