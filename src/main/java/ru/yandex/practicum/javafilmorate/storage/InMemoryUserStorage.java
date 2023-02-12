package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.ValidationException1;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

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

        if (storages.size() != 0)
        if (storages.keySet().stream().filter(e -> e == generateId).findFirst().isPresent())
            throw new ValidationException1("Пользователь " + user.getName() + " уже существует");

        storages.put(generateId, user);
        log.info("Creating user {}", user);
        return user;
    }

    @Override
    public User update(User user) {
        generateId = user.getId();
        storages.keySet().stream().forEach(e-> System.out.println(e));
        storages.keySet().stream().filter(e -> e == generateId).findFirst()
                .orElseThrow(() -> new ValidationException1("Пользователя " + user.getName() + " в списке пользователей нет"));

        log.info("Пользователь " + user.getName() + " обновлен");
        storages.put(generateId, user);

        return user;
    }

    @Override
    public void deleteAll() {
        storages.clear();
    }


    public User getById(Long id){
        return storages.get(id);
    }

}
