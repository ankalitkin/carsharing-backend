package ru.vsu.cs.carsharing.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.vsu.cs.carsharing.exception.WebException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = WebException.class)
    protected ResponseEntity<Object> handleItemNotFound(WebException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        HttpStatus httpStatus = ex.getHttpStatus();
        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String response = String.join(System.lineSeparator(), errors);
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
