package com.flax.finplat.test;

import com.flax.finplat.model.Operation;
import com.flax.finplat.repository.OperationRepository;
import com.flax.finplat.test.client.OperationClient;
import com.flax.finplat.util.IdGenerator;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.flax.finplat.test.ActiveTestProfiles.TEST;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(TEST)
public class MyOperationControllerTest {

    @Autowired
    private OperationClient client;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private IdGenerator idGenerator;


    @SneakyThrows
    @Test
    public void viewMyOperations() {
        client.getOperations()
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].amount", is(equalTo("200.0000"))))
                .andExpect(jsonPath("$[0].comment", is(equalTo("comment"))))
                .andExpect(jsonPath("$[0].currency", is(equalTo("hrn"))))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].amount", is(equalTo("200.0000"))))
                .andExpect(jsonPath("$[1].comment", is(equalTo("comment 2"))))
                .andExpect(jsonPath("$[1].currency", is(equalTo("usd"))));
    }

    @SneakyThrows
    @Test
    public void createOperation() {
        client.createOperation(OffsetDateTime.now(), "hrn", "200", "new operation")
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.amount", is(equalTo("200.0000"))))
                .andExpect(jsonPath("$.comment", is(equalTo("new operation"))))
                .andExpect(jsonPath("$.currency", is(equalTo("hrn"))))
                .andDo(mvcResult -> {
                    String id = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.id");
                    client.deleteOperation(id);
                });
    }

    @SneakyThrows
    @Test
    public void deleteOperation() {
        Operation newFirstOperation = operationRepository.save(Operation.createEmpty(OffsetDateTime.now(),
                                                                                     "hrn",
                                                                                     BigDecimal.valueOf(200),
                                                                                     "comment"));
        Operation newSecondOperation = operationRepository.save(Operation.createEmpty(OffsetDateTime.now(),
                                                                                      "usd",
                                                                                      BigDecimal.valueOf(200),
                                                                                      "comment 2"));
        client.getOperations()
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));
        client.deleteOperation(newFirstOperation.getId()).andExpect(status().isOk());
        client.deleteOperation(newSecondOperation.getId()).andExpect(status().isOk());
        client.getOperations()
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @SneakyThrows
    @Test
    public void updateOperation() {
        Operation newFirstOperation = operationRepository.save(Operation.createEmpty(OffsetDateTime.now(),
                                                                                     "hrn",
                                                                                     BigDecimal.valueOf(200),
                                                                                     "comment"));
        client.getOperations()
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
        client.updateOperation(newFirstOperation.getId(),
                               OffsetDateTime.now(),
                               "eur",
                               BigDecimal.valueOf(400),
                               "update operation")
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(newFirstOperation.getId()))))
                .andExpect(jsonPath("$.amount", is(equalTo("400.0000"))))
                .andExpect(jsonPath("$.comment", is(equalTo("update operation"))))
                .andExpect(jsonPath("$.currency", is(equalTo("eur"))))
                .andDo(mvcResult-> {
                    String id = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.id");
                    client.deleteOperation(id);
                });
    }


    @SneakyThrows
    @Test
    public void updateOperation_operationNotFound() {
        client.updateOperation(idGenerator.nextId(),
                               OffsetDateTime.now(),
                               "eur",
                               BigDecimal.valueOf(400),
                               "update operation")
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @SneakyThrows
    @Test
    public void deleteOperation_operationNotFound() {
        client.deleteOperation(idGenerator.nextId())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }
}