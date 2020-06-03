package com.flax.finplat.test.client;

import com.flax.finplat.util.Calculator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.flax.finplat.test.client.JsonTemplates.OPERATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
public class OperationClient {

    private static final String OPERATIONS = "/my/finances/operations/";
    @Autowired
    protected MockMvc mvc;

    @SneakyThrows
    public ResultActions getOperations() {
        return mvc.perform(get(OPERATIONS).accept(APPLICATION_JSON));
    }

    @SneakyThrows
    public ResultActions createOperation(OffsetDateTime date, String currency, String amount, String comment) {
        return mvc.perform(post(OPERATIONS)
                                   .contentType(APPLICATION_JSON)
                                   .content(String.format(OPERATION,
                                                          date.toString(),
                                                          currency,
                                                          Calculator.normalize(amount).toPlainString(),
                                                          comment))
        );
    }

    @SneakyThrows
    public ResultActions updateOperation(String id, OffsetDateTime date, String currency, BigDecimal amount,
                                         String comment) {
        return mvc.perform(put(OPERATIONS + id)
                                   .contentType(APPLICATION_JSON)
                                   .content(String.format(OPERATION,
                                                          date.toString(),
                                                          currency,
                                                          amount.toPlainString(),
                                                          comment))
        );
    }

    @SneakyThrows
    public ResultActions deleteOperation(String operationId) {
        return mvc.perform(delete(OPERATIONS + operationId).contentType(APPLICATION_JSON));
    }


}
