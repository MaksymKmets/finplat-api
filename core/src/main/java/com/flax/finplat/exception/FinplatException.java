package com.flax.finplat.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * General REST exception
 */
@Getter
public class FinplatException extends RuntimeException implements SystemException {

    protected final HttpStatus status;

    public String getCode() {
        return getClass().getSimpleName()
                .replaceAll("Exception", "")
                .replaceAll("([A-Z]+)([A-Z])([a-z])", "$1_$2$3")
                .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
                .toUpperCase();
    }

    protected FinplatException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    protected FinplatException( HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
