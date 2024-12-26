package com.vou.backend.user.exception;

public class UserEmailExistedException extends Exception {
    public UserEmailExistedException(String message) {
        super(message);
    }
}
