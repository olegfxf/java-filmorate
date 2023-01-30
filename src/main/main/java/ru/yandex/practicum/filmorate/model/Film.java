package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.util.Random;

import java.time.LocalDate;

@Data

public class Film {
    private int id = Uid.getUid();
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

    public static Film testFilm() {
        Film film = new Film();
        film.setId(Uid.getUid());
        film.setName(Random.string(5));
        film.setDescription(Random.string(50));
        film.setReleaseDate(LocalDate.now());
        film.setDuration(128);
        return film;
    }
}
