package com.zepl.test.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String user) {
        super(user + " not found");
    }
}
