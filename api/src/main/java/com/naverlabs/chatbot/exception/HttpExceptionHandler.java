package com.naverlabs.chatbot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author minhyeok
 */
@ControllerAdvice
public class HttpExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(ResourceNotFoundException e, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.NOT_FOUND;
        response.sendError(status.value(), status.getReasonPhrase());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        response.sendError(status.value(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletResponse response) throws IOException {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        response.sendError(status.value(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletResponse response) throws IOException {
        logger.warn(e.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.sendError(status.value(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletResponse response) throws IOException {
        logger.warn(e.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.sendError(status.value(), status.getReasonPhrase());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletResponse response) throws IOException {
        logger.warn(e.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.sendError(status.value(), status.getReasonPhrase());
    }

    @ExceptionHandler(Throwable.class)
    public void handleException(Throwable e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage(), e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        response.sendError(status.value(), status.getReasonPhrase());
    }
}