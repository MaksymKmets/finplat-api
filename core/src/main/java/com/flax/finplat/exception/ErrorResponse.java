package com.flax.finplat.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.util.List;

@ApiModel(description = "Errors information response")
@Value(staticConstructor = "valueOf")
public class ErrorResponse {

    @ApiModelProperty("List of errors")
    List<ApiError> errors;

    @ApiModel(description = "Error data")
    @Value(staticConstructor = "valueOf")
    public static class ApiError {
        @ApiModelProperty("Code of error")
        String code;
        @ApiModelProperty("Message of error")
        String message;
    }
}
