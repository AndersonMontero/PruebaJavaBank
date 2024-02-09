package com.bank.prueba.web.config;

import com.bank.prueba.domain.exception.HttpGenericException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(value = HttpGenericException.class)
    protected ResponseEntity<Object> handleHttpGenericException(HttpGenericException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getErrorData(), new HttpHeaders(), exception.getHttpStatus(), request);
    }

}
