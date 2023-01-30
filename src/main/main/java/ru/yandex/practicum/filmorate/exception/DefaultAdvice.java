package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultAdvice {

    @ExceptionHandler({
            UserAlreadyExist.class,
            InvalidEmailException.class,
            UserCreateFailEmail.class,
            CreateUserWithEmptyName.class,
            UserCreateFailBirthday.class,

            FilmEmptyName.class,
            FilmFailDurationNegative.class,
            FilmFailReleaseDate.class,
            FilmWithEmptyName.class,
            FilmUpdateUnknown.class
    })
    public ResponseEntity<Response> handleException(Exception e) {
        log.error(e.getMessage(), e);
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}