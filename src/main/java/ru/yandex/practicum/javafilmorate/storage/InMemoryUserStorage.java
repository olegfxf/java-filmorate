package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.ValidationException404;
import ru.yandex.practicum.javafilmorate.exception.ValidationException500;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> storages = new HashMap<>();
    Long generateId = 1L;

    @Override
    public ArrayList<User> getAll() {
        return (ArrayList<User>) storages.values().stream().collect(Collectors.toList());
    }

    @Override
    public User getById(Long id) {
        storages.keySet().stream().filter(e -> e.equals(id)).findFirst()
                .orElseThrow(() -> new ValidationException404("Пользователя с id = " + id
                        + " в списке пользователей нет"));

        return storages.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(generateId++);
        if (storages.size() != 0)
            if (storages.keySet().stream().filter(e -> e.equals(user.getId())).findFirst().isPresent())
                throw new ValidationException500("Пользователь " + user.getName() + " уже существует");
        storages.put(user.getId(), user);
        log.info("Creating user {}", user);

        return user;
    }

    @Override
    public User update(User user) {
        storages.keySet().stream().filter(e -> e.equals(user.getId())).findFirst()
                .orElseThrow(() -> new ValidationException500("Пользователя " + user.getName() + " в списке пользователей нет"));
        log.info("Пользователь " + user.getName() + " обновлен");
        storages.put(user.getId(), user);

        return user;
    }

    @Override
    public void deleteAll() {
        storages.clear();
    }

}
