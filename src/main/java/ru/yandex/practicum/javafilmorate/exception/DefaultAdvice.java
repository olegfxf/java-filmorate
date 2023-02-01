package ru.yandex.practicum.javafilmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultAdvice {

    @ExceptionHandler({
            ValidationException.class,
 //           UserAlreadyExist1.class,
 //           InvalidEmailException1.class,

            //CreateUserWithEmptyName.class,



            //FilmEmptyName.class,
            //FilmFailDurationNegative.class,
            //FilmFailReleaseDate.class,
            //FilmWithEmptyName1.class,
            //FilmUpdateUnknown.class
    })
    public ResponseEntity<Response> handleException(Exception e) {
        log.error(e.getMessage(), e);
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}