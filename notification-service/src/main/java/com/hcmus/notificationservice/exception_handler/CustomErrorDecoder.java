package com.hcmus.notificationservice.exception_handler;

import com.hcmus.notificationservice.exception.CustomFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String responseBody = response.body() != null ? new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8) : "Unknown error";
            // Return a custom exception with the original and modified body
            return new CustomFeignException(response.status(), responseBody);
        } catch (IOException e) {
            return new Exception("Error decoding response: " + e.getMessage(), e);
        }
    }
}