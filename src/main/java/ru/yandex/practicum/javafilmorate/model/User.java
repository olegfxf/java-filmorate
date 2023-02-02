package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.yandex.practicum.javafilmorate.custom.JsonDateDeserializer;
import ru.yandex.practicum.javafilmorate.custom.JsonDateSerializer;
import ru.yandex.practicum.javafilmorate.util.Random;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static Uid uid = new Uid();
    private Long id = uid.getUid();
    @NotBlank
    @NotNull
    @Pattern(regexp = "\\S+")
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
