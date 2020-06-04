package com.flax.finplat.controller;

import com.flax.finplat.dto.SaveOperationReq;
import com.flax.finplat.model.Operation;
import com.flax.finplat.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Api(tags = "User operation management")
@Validated
@RestController
@RequestMapping("my/finances/operations")
public class MyOperationController {

    private final OperationService operationService;

    @ApiOperation(value = "Get list of operations",
            produces = APPLICATION_JSON_VALUE)
    @GetMapping
    public List<Operation> viewMyOperations() {
        return operationService.getOperations();
    }

    @ApiOperation(value = "Create a new operation",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Operation createOperation(@RequestBody @Valid SaveOperationReq req) {
        return operationService.createOperation(req.getDate(), req.getCurrency(),
                                                req.getAmount(), req.getComment());
    }

    @ApiOperation(value = "Delete an operation")
    @DeleteMapping("/{operationId}")
    public void deleteOperation(@PathVariable("operationId") String operationId) {
        operationService.deleteOperation(operationId);
    }

    @ApiOperation(value = "Update an operation")
    @PutMapping("/{operationId}")
    public Operation updateOperation(@PathVariable("operationId") String operationId,
                                     @RequestBody @Valid SaveOperationReq req) {
        return operationService.updateOperation(operationId, req.getDate(),
                                                req.getCurrency(), req.getAmount(),
                                                req.getComment());
    }
}
