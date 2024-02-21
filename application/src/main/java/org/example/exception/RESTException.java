package org.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RESTException extends RuntimeException {

    private HttpStatus httpStatus;
    private final String message;

    public RESTException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public RESTException(String message) {
        this.message = message;
    }
}
