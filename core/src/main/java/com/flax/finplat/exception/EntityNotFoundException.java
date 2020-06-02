package com.flax.finplat.exception;

import org.springframework.http.HttpStatus;

/**
 * Not found general exception for different entities
 */
public class EntityNotFoundException extends FinplatException {

    protected EntityNotFoundException(String template, String id) {
        super(HttpStatus.NOT_FOUND, String.format(template, id));
    }
}
