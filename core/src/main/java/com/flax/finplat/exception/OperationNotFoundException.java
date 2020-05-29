package com.flax.finplat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OperationNotFoundException extends EntityNotFoundException {
    public OperationNotFoundException(String operationId) {
        super("Operation with id %s is not found", operationId);
    }
}
