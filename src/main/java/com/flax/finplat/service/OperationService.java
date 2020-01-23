package com.flax.finplat.service;

import com.flax.finplat.model.Operation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public interface OperationService {

    List<Operation> getOperations();

    Operation createOperation(OffsetDateTime date, String currency, BigDecimal amount, String comment);

    void deleteOperation(String operationId);

    Operation findOperationRequired(String operationId);

    Operation updateOperation(String operationId, OffsetDateTime date, String currency, BigDecimal amount,
                              String comment);
}
