package com.zepl.test.handler;

import com.zepl.test.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public String userNotFoundExceptionHandler(UserNotFoundException e) {
        return e.getMessage();
    }
}
