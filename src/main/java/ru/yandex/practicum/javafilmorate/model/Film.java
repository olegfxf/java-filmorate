package ru.yandex.practicum.javafilmorate.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film implements Comparable<Film> {

    static Uid uid = new Uid();
    Long id = uid.getUid();
    @NotBlank(message = "наименование фильма не должно быть пустым")
    @NotNull(message = "наименование фильма обязательно")
    String name;
    @Size(max = 200, message = "описание фильма не может иметь больше 200 символов")
    String description;
    @NotNull(message = "дата выпуска релиза обязательна")
    @ReleaseDate(message = "дата выпуска релиза должна быть после 28.12.1895")
    LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной")
    Integer duration;
    Set<Long> likes = new HashSet<>();


    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void deleteLike(Long userId) {
        likes.remove(userId);
    }

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
