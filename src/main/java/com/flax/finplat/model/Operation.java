package com.flax.finplat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private OffsetDateTime date;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    private String currency;

    private String comment;

    public static Operation createEmpty(OffsetDateTime date, String currency, BigDecimal amount, String comment) {
        Operation operation = new Operation();
        operation.date = date;
        operation.currency = currency;
        operation.amount = amount;
        operation.comment = comment;
        return operation;
    }

    public void update(OffsetDateTime date, String currency, BigDecimal amount, String comment) {
        this.date = date;
        this.currency = currency;
        this.amount = amount;
        this.comment = comment;
    }
}
