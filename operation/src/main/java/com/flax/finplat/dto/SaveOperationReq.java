package com.flax.finplat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@ApiModel(description = "Save operation DTO")
@Data
public class SaveOperationReq {
    @NotNull
    @ApiModelProperty("Operation date")
    private OffsetDateTime date;

    @NotNull
    @Positive
    @ApiModelProperty("Operation amount")
    private BigDecimal amount;

    @NotBlank
    @Size(min = 3,max=3)
    @ApiModelProperty("Operation currency")
    private String currency;

    @ApiModelProperty("Comment")
    private String comment;
}
