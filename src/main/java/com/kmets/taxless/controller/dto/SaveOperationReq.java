package com.kmets.taxless.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class SaveOperationReq {

    private OffsetDateTime date;

    private BigDecimal amount;

    private String currency;

    private String comment;
}
