package com.vou.backend.user.exception;

public class UserNameExistedException extends Exception {
    public UserNameExistedException(String message) {
        super(message);
    }
}
