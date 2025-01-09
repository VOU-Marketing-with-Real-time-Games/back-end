package com.hcmus.user_service.exception;

public class UserEmailExistedException extends Exception {
    public UserEmailExistedException(String message) {
        super(message);
    }
}
