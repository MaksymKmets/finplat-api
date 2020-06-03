package com.flax.finplat.exception;

import com.flax.finplat.util.EntityNotFoundException;

public class OperationNotFoundException extends EntityNotFoundException {
    public OperationNotFoundException(String operationId) {
        super("Operation with id %s is not found", operationId);
    }
}
