package ru.yandex.practicum.javafilmorate.controller.model;

public class Uid {
    static int uid = 1;

    static Integer getUid() {
        return uid++; // new task id
    }
}
