package com.bank.prueba.domain.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpGenericException extends RuntimeException{
    private final HttpStatus httpStatus;

    public HttpGenericException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, Object> getErrorData() {
        String message = getMessage();

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("type", "error");
        errorMap.put("mensaje", message);

        return errorMap;
    }
}
