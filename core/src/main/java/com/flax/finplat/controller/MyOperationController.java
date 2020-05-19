package com.flax.finplat.controller;

import com.flax.finplat.controller.dto.SaveOperationReq;
import com.flax.finplat.model.Operation;
import com.flax.finplat.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("my/finances/operations")
public class MyOperationController {

    private OperationService operationService;

    @Autowired
    public MyOperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public List<Operation> viewMyOperations() {
        return operationService.getOperations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Operation createOperation(@RequestBody SaveOperationReq req) {
        return operationService.createOperation(req.getDate(), req.getCurrency(), req.getAmount(), req.getComment());
    }

    @DeleteMapping("/{operationId}")
    public void deleteOperation(@PathVariable("operationId") String operationId) {
        operationService.deleteOperation(operationId);
    }

    @PutMapping("/{operationId}")
    public Operation updateOperation(@PathVariable("operationId") String operationId,
                                     @RequestBody SaveOperationReq req) {
        return operationService.updateOperation(operationId, req.getDate(), req.getCurrency(), req.getAmount(),
                                                req.getComment());
    }
}
