package ru.yandex.practicum.javafilmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.javafilmorate.controller.FilmController;
import ru.yandex.practicum.javafilmorate.controller.UserController;


@ControllerAdvice(assignableTypes = {FilmController.class, UserController.class})
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Slf4j
public class DefaultAdvice {
    @ExceptionHandler({ValidationException.class})
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        //ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ErrorResponse("Ошибка данных", e.getMessage());//.INTERNAL_SERVER_ERROR);
    }

}