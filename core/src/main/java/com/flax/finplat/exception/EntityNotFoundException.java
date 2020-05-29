package com.flax.finplat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not found general exception for different entities
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends FinplatException {

    protected EntityNotFoundException(String template, String id) {
        super(String.format(template, id), DigiPlugExceptionType.ENTITY_NOT_FOUND);
    }
}
