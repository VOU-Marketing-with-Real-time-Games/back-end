package com.hcmus.auth_service.exception;

public class NotActiveAccountException extends RuntimeException {
    public NotActiveAccountException(String message) {
        super(message);
    }
}
