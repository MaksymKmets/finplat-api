package com.kmets.taxless.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OperationNotFoundException extends RuntimeException {

    public OperationNotFoundException(String operationId) {
        super(String.format("Operation with id %s is not found", operationId));
    }
}
