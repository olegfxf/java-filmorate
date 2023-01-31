package ru.yandex.practicum.javafilmorate.model;

public class Uid {
    static int uid = 1;

    public static Integer getUid() {
        return uid++; // new task id
    }
}
