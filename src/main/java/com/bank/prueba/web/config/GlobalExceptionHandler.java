package com.bank.prueba.web.config;

import com.bank.prueba.domain.exception.HttpGenericException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.FieldError;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = HttpGenericException.class)
    protected ResponseEntity<Object> handleHttpGenericException(HttpGenericException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getErrorData(), new HttpHeaders(), exception.getHttpStatus(), request);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        Map<String, String> errorData = Map.of(
                "type", "error",
                "message", "No se encontró el elemento solicitado");
        return handleExceptionInternal(exception, errorData, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = SQLException.class)
    protected ResponseEntity<Object> handleSQLException(SQLException exception, WebRequest request) {
        Map<String, String> errorData = Map.of(
                "type", "error",
                "message", exception.getMessage());
        return handleExceptionInternal(exception, errorData, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "validation_error");
        body.put("message", "Validation failed");
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (msg1, msg2) -> msg1 + "; " + msg2));
        body.put("errors", fieldErrors);
        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("type", "validation_error");
        body.put("message", "Validation failed");
        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(cv -> cv.getPropertyPath().toString(), cv -> cv.getMessage(), (m1, m2) -> m1 + "; " + m2));
        body.put("errors", errors);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
