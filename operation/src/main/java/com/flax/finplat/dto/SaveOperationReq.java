package com.flax.finplat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@ApiModel(description = "Save operation DTO")
@Data
public class SaveOperationReq {
    @ApiModelProperty("Operation date")
    private OffsetDateTime date;
    @ApiModelProperty("Operation amount")
    private BigDecimal amount;
    @ApiModelProperty("Operation currency")
    private String currency;
    @ApiModelProperty("Comment")
    private String comment;
}
