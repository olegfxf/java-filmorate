package ru.yandex.practicum.javafilmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.ValidationException404;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(final Long id, final Long friendId) {
        if (id < 0 || friendId < 0)
            throw new ValidationException404("Отрицательный идентификатор");
        User user = userStorage.getById(id);
        user.friends.add(friendId);
        userStorage.update(user);

        user = userStorage.getById(friendId);
        user.friends.add(id);
        userStorage.update(user);
        return userStorage.getById(id);
    }

    public User deleteFriend(final Long id, final Long friendId) {
        User user = userStorage.getById(id);
        user.friends.remove(friendId);
        userStorage.update(user);

        return user;
    }

    public List<User> getAllFriends(final Long id) {
        Set<Long> idUsers = userStorage.getById(id).friends;
        List<User> users = userStorage.getAll().stream().filter(e -> idUsers.contains(e.getId())).collect(Collectors.toList());
        return users;
    }

    public List<User> commonFriend(final Long id, final Long otherId) {
        User user = userStorage.getById(id);
        User userOther = userStorage.getById(otherId);

        Set<Long> friends = user.friends;
        Set<Long> friends2 = new HashSet<>(friends);

        Set<Long> friendsOther = userOther.friends;
        friends2.retainAll(friendsOther);

        List<User> users = userStorage.getAll().stream().filter(e -> friends2.contains(e.getId())).collect(Collectors.toList());

        return users;

    }
}
