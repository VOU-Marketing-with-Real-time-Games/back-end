package com.hcmus.user_service.exception;

public class UserNameExistedException extends Exception {
    public UserNameExistedException(String message) {
        super(message);
    }
}
