package ru.yandex.practicum.javafilmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.javafilmorate.constraints.ReleaseDate;
import ru.yandex.practicum.javafilmorate.util.Random;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class Film implements Comparable<Film> {

    private static Uid uid = new Uid();
    private Long id = uid.getUid();
    @NotBlank(message = "наименование фильма не должно быть пустым")
    @NotNull(message = "наименование фильма обязательно")
    private String name;
    @Size(max = 200, message = "описание фильма не может иметь больше 200 символов")
    private String description;
    @NotNull(message = "дата выпуска релиза обязательна")
    @ReleaseDate(message = "дата выпуска релиза должна быть после 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной")
    private Integer duration;
    public Set<Long> likes = new HashSet<>();

    public Film testFilm() {
        Film film = new Film();
        film.setId(uid.getUid());
        film.setName(Random.string(5));
        film.setDescription(Random.string(50));
        film.setReleaseDate(LocalDate.now());
        film.setDuration(128);
        return film;
    }

    @Override
    public int compareTo(Film film) {
        return this.likes.size() - film.likes.size();
    }
}
