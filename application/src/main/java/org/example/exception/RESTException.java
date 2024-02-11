package org.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RESTException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public RESTException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
