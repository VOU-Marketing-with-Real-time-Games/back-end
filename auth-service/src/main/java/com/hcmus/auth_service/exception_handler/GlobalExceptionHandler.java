package com.hcmus.auth_service.exception_handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmus.auth_service.exception.*;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Date;

/**
 * Global exception handler for the application.
 * This class handles various exceptions and provides appropriate responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Handles duplication errors and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the Exception
     * @return an ErrorDTO containing error details
     */


    /**
     * Handles Feign client exceptions and directly returns the Feign response body.
     */
    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<JsonNode> handleCustomFeignException(CustomFeignException ex) {
        LOGGER.error("Custom Feign Client Error: {}", ex.getMessage(), ex);

        HttpStatus status = HttpStatus.resolve(ex.getStatus());
        JsonNode errorJson;
        try {
            errorJson = objectMapper.readTree(ex.getMessage());
        } catch (Exception e) {
            errorJson = objectMapper.createObjectNode().put("error", "Failed to parse error message");
        }
        return ResponseEntity.status(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorJson);
    }
    /**
     * Handles validation errors and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the MethodArgumentNotValidException
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.BAD_REQUEST.value());

        // Add validation error messages
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addError(fieldError.getDefaultMessage());
        });

        // Logging errors
        LOGGER.error("Validation error: {}", ex.getBindingResult().getAllErrors(), ex);

        return error;
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(ex.getMessage());
        LOGGER.error("Runtime Exception: {}", ex.getMessage(), ex);
        return error;
    }
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGeneralException(HttpServletRequest request, Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            return handleValidationExceptions(request, (MethodArgumentNotValidException) ex);
        }
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.addError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        LOGGER.error("Internal Server Error: {}", ex.getMessage(), ex);
        return error;
    }
}
