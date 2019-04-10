package com.mdem.cms.controller.common;

import com.mdem.cms.exception.*;
import com.mdem.cms.model.common.ErrorInfo;
import com.mdem.cms.model.common.ValidationError;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionController /*extends ResponseEntityExceptionHandler*/ {

    private Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(ConflictDataException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorInfo conflictDataException(HttpServletRequest request, ConflictDataException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.CONFLICT.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorInfo dataNotFoundException(HttpServletRequest request, DataNotFoundException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.NOT_FOUND.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(NoDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo noDataException(HttpServletRequest request, NoDataException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorInfo userNotFoundException(HttpServletRequest request, UserNotFoundException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorInfo incorrectPasswordException(HttpServletRequest request, IncorrectPasswordException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorInfo methodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException exception)   {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.METHOD_NOT_ALLOWED.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorInfo mediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException exception)   {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo internalServerErrorException(HttpServletRequest request, HttpServerErrorException exception) {
        String errorURL = request.getRequestURL().toString();
        String errorMessage = exception.getMessage();
        logger.error(errorMessage, exception);
        return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorURL, errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationError argumentNotValidException(MethodArgumentNotValidException exception)   {
        Map<String, String> validationErrors = new HashMap<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        logger.error(validationErrors.toString(), exception);
        return new ValidationError(validationErrors);
    }
}
