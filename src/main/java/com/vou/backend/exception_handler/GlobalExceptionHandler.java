package com.vou.backend.exception_handler;

import com.vou.backend.campaign.exception.CampaignNotFoundException;
import com.vou.backend.game.game_info.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

/**
 * Global exception handler for the application.
 * This class handles various exceptions and provides appropriate responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the MethodArgumentNotValidException
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
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

    /**
     * Handles Game_CampaignGameConflict exceptions and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the exception
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler({Game_CampaignGameConflict.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleBadRequestException(HttpServletRequest request, Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(ex.getMessage());
        LOGGER.error("Not Found: {}", ex.getMessage(), ex);
        return error;
    }

    /**
     * Handles not found exceptions and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the exception
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler({GameNotFoundException.class, GameCampaignNotFoundException.class, UserCampaignGameNotFoundException.class, PuzzleNotFoundException.class, QuizzNotFoundException.class, QuestionNotFoundException.class
    , CampaignNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleNotFoundException(HttpServletRequest request, Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setPath(request.getServletPath());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.addError(ex.getMessage());
        LOGGER.error("Not Found: {}", ex.getMessage(), ex);
        return error;
    }

    /**
     * Handles general exceptions and returns an ErrorDTO.
     *
     * @param request the HTTP request
     * @param ex      the exception
     * @return an ErrorDTO containing error details
     */
    @ExceptionHandler(Exception.class)
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
