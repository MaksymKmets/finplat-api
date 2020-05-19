package com.flax.finplat.service;

import com.flax.finplat.repository.OperationRepository;
import com.flax.finplat.service.exception.OperationNotFoundException;
import com.flax.finplat.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Transactional
@Service
public class OperationServiceImpl implements OperationService {

    private OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Operation> getOperations() {
        return operationRepository.findAll();
    }

    @Override
    public Operation createOperation(OffsetDateTime date, String currency, BigDecimal amount, String comment) {
        Operation operation = Operation.createEmpty(date, currency, amount, comment);
        operation = operationRepository.save(operation);
        return operation;
    }

    @Override
    public void deleteOperation(String operationId) {
        Operation operation = findOperationRequired(operationId);
        operationRepository.delete(operation);
    }

    @Override
    public Operation findOperationRequired(String operationId) {
        return operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
    }

    @Override
    public Operation updateOperation(String operationId, OffsetDateTime date, String currency, BigDecimal amount,
                                     String comment) {
        Operation operation = findOperationRequired(operationId);
        operation.update(date, currency, amount, comment);
        return operationRepository.save(operation);
    }
}
