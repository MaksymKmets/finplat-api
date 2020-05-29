package com.flax.finplat.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General REST exception
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FinplatException extends RuntimeException {
    @Getter
    protected final DigiPlugExceptionType code;

    public FinplatException(String message, DigiPlugExceptionType code) {
        super(message);
        this.code = code;
    }

    public FinplatException(String message) {
        super(message);
        this.code = DigiPlugExceptionType.INTERNAL_SERVER_ERROR;
    }
}
