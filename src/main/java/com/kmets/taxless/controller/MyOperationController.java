package com.kmets.taxless.controller;

import com.kmets.taxless.controller.dto.SaveOperationReq;
import com.kmets.taxless.model.Operation;
import com.kmets.taxless.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Operation createOperation(@RequestBody SaveOperationReq req) {
        return operationService.createOperation(req.getDate(), req.getCurrency(), req.getAmount(), req.getComment());
    }

    @DeleteMapping("{operationId}")
    public void deleteOperation(@PathVariable("operationId") String operationId) {
        operationService.deleteOperation(operationId);
    }

    @PutMapping("{operationId}")
    public Operation updateOperation(@PathVariable("operationId") String operationId,
                                     @RequestBody SaveOperationReq req) {
        return operationService.updateOperation(operationId, req.getDate(), req.getCurrency(), req.getAmount(),
                                                req.getComment());
    }
}
