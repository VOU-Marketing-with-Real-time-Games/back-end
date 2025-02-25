package com.hcmus.notificationservice.exception;

public class CustomFeignException extends RuntimeException {
    private final int status;

    public CustomFeignException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}