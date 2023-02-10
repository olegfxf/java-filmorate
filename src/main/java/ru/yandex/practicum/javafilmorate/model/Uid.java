package ru.yandex.practicum.javafilmorate.model;

public class Uid {
    Long uid = 1L;

    public Long getUid() {
        return uid++;
    }
}
