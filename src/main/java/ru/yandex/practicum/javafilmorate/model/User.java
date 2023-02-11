package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.javafilmorate.custom.JsonDateDeserializer;
import ru.yandex.practicum.javafilmorate.custom.JsonDateSerializer;
import ru.yandex.practicum.javafilmorate.util.Random;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class User {

    private static Uid uid = new Uid();
    private Long id = uid.getUid();
    private String login;
    private String name;
    @NotBlank
    @NotNull
    @Email
    private String email;

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
