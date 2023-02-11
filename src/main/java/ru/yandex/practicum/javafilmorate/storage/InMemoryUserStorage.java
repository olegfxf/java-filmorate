package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUserStorage implements UserStorage{
    public final HashMap<Long, User> storages = new HashMap<>();



    Long generateId = -1L;

    @Override
    public List<User> getAll() {
        //log.info("Выполнен запрос на вывод всех пользователей");
        return storages.values().stream().collect(Collectors.toList());
    }

    @Override
    public User create(User user) {
        generateId = user.getId();
        storages.put(generateId, user);

        return user;
    }

    @Override
    public User update(User user) {
        generateId = user.getId();
        if (storages.keySet().stream().filter(e -> e == generateId).findFirst().isPresent())
            storages.put(generateId, user);

        return user;
    }

    @Override
    public void deleteAll() {
        storages.clear();
    }
}
