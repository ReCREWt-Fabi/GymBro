package de.othr.im.gymbro.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ExceptionHandler(NotLoggedInException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNotLoggedInException(NotLoggedInException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleInvalidAccessException(InvalidAccessException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e) {
        return e.getMessage();
    }

}
