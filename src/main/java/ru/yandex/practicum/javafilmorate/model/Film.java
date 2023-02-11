package ru.yandex.practicum.javafilmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.javafilmorate.util.Random;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Film {
    private static Uid uid = new Uid();
    private Long id = uid.getUid();
    @NotBlank
    @NotNull
    private String name;
    @Size(max = 200, message = "описание фильма не может иметь больше 200 символов")
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private Integer duration;

    public Film testFilm() {
        Film film = new Film();
        film.setId(uid.getUid());
        film.setName(Random.string(5));
        film.setDescription(Random.string(50));
        film.setReleaseDate(LocalDate.now());
        film.setDuration(128);
        return film;
    }
}
