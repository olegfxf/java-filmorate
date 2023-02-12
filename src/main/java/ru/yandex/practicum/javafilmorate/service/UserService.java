package ru.yandex.practicum.javafilmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.Set;

@Component
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User addFriend(final Long id, final Long friendId) {
        User user = inMemoryUserStorage.getById(id);
        user.friends.add(friendId);
        inMemoryUserStorage.storages.put(id, user);

        return user;
    }

    public User deleteFriend(final Long id, final Long friendId) {
        User user = inMemoryUserStorage.getById(id);
        user.friends.remove(friendId);
        inMemoryUserStorage.storages.remove(id, user);

        return user;
    }

    public Set<Long> getALL(final Long id) {
        Set<Long> idUsers = inMemoryUserStorage.getById(id).friends;
        return idUsers;
    }

    public Set<Long> commonFriend(final Long id, final Long otherId) {
        User user = inMemoryUserStorage.getById(id);
        User userOther = inMemoryUserStorage.getById(otherId);

        System.out.println("wwwwwwwwwwwwwwwww " + id + " ddddddddddddddd " + otherId);
       Set<Long> qqq = user.friends;
       Set<Long> qqq2 = userOther.friends;
       qqq.retainAll(qqq2);
       System.out.println("wwwwwwwwwwwwwwwww " + qqq.toString());
       return qqq;

    }
}
