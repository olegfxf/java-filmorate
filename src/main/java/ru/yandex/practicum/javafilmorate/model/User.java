package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.javafilmorate.constraints.BirthDate;
import ru.yandex.practicum.javafilmorate.custom.JsonDateDeserializer;
import ru.yandex.practicum.javafilmorate.custom.JsonDateSerializer;
import ru.yandex.practicum.javafilmorate.util.Random;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
public class User {

    private static Uid uid = new Uid();
    private Long id;
    @NotBlank(message = "login содержит пробел")
    @NotNull(message = "login обязателен")
    private String login;
    private String name;
    @NotBlank(message = "адрес электронной почты содержит пробел")
    @NotNull(message = "адрес электронной почты обязателен")
    @Email(message = "некорректный адрес электронной почты")
    private String email;
    public Set<Long> friends = new HashSet<>();

    @NotNull(message = "дата дня рождения обязательна")
    @BirthDate(message = "дата дня рождения должна быть меньше текущей даты")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate birthday;

    public User testUser() {
        User user = new User();
        user.setId(uid.getUid());
        user.setLogin(Random.string(5));
        user.setName(Random.string(5));
        user.setEmail(Random.string(5) + "@" + Random.string(10));
        user.setBirthday(LocalDate.now());

        return user;
    }

}
