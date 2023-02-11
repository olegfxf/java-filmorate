package ru.yandex.practicum.javafilmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.ErrorResponse;
import ru.yandex.practicum.javafilmorate.model.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleValidateException1(final ValidationException1 e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse("Ошибка данных", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<Violation> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> errorResponses = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ArrayList<Violation>(errorResponses);
    }
}