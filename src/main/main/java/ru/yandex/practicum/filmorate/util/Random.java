package ru.yandex.practicum.filmorate.util;

import java.util.stream.Collectors;

public class Random {
    public static String string(int size) {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String random = new java.util.Random().ints(size, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return random;
    }
}
