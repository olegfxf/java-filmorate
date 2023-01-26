package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.yandex.practicum.filmorate.custom.JsonDateDeserializer;
import ru.yandex.practicum.filmorate.custom.JsonDateSerializer;

import java.rmi.server.UID;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class User {
    private int id = Uid.getUid();
    private String login;
    private String name;
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate birthday;
}
