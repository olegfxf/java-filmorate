package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.ValidationException404;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage)
    {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User addFriend(final Long id, final Long friendId) {
        if (id < 0 || friendId < 0)
            throw new ValidationException404("Отрицательный идентификатор");
        User user = inMemoryUserStorage.getById(id);
        user.friends.add(friendId);
        inMemoryUserStorage.storages.put(id, user);

        user = inMemoryUserStorage.getById(friendId);
        user.friends.add(id);
        inMemoryUserStorage.storages.put(friendId, user);
        return inMemoryUserStorage.storages.get(id);//user;
    }

    public User deleteFriend(final Long id, final Long friendId) {
        User user = inMemoryUserStorage.getById(id);
        user.friends.remove(friendId);
        inMemoryUserStorage.storages.put(id, user);

        return user;
    }

    public List<User> getAllFriends(final Long id) {
        Set<Long> idUsers = inMemoryUserStorage.getById(id).friends;
        List<User> users = inMemoryUserStorage.getAll().stream().filter(e->idUsers.contains(e.getId())).collect(Collectors.toList());
        return users;
    }

    public List<User> commonFriend(final Long id, final Long otherId) {
        User user = inMemoryUserStorage.getById(id);
        User userOther = inMemoryUserStorage.getById(otherId);

        Set<Long> friends = user.friends;
        Set<Long> friends2 = new HashSet<>(friends);

        Set<Long> friendsOther = userOther.friends;
        friends2.retainAll(friendsOther);

        System.out.println("wwwwwwwwwwwwwwwww common" + friends2.toString());
        List<User> users = inMemoryUserStorage.getAll().stream().filter(e->friends2.contains(e.getId())).collect(Collectors.toList());

        return users;

    }
}
