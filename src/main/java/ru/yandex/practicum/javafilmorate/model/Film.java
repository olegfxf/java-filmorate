package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.javafilmorate.util.Random;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Film {
    private static Uid uid = new Uid();
    private Long id = uid.getUid();
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

    public static Film testFilm() {
        Film film = new Film();
        film.setId(uid.getUid());
        film.setName(Random.string(5));
        film.setDescription(Random.string(50));
        film.setReleaseDate(LocalDate.now());
        film.setDuration(128);
        return film;
    }
}
