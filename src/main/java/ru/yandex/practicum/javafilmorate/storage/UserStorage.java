package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getAll();
    User getById(Long id);
    User create(User user);
    User update(User user);
    void deleteAll();

}
