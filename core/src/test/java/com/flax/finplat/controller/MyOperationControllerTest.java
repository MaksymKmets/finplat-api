package com.flax.finplat.controller;

import com.flax.finplat.FinplatApplication;
import com.flax.finplat.config.WebConfig;
import com.flax.finplat.model.Operation;
import com.flax.finplat.repository.OperationRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FinplatApplication.class, WebConfig.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class MyOperationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OperationRepository operationRepository;

    private static final String OPERATIONS = "/my/finances/operations";

    @SneakyThrows
    @Test
    public void viewMyOperations() {
        mvc.perform(get(OPERATIONS)
                            .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].amount", is(equalTo(200.0))))
                .andExpect(jsonPath("$[0].comment", is(equalTo("comment"))))
                .andExpect(jsonPath("$[0].currency", is(equalTo("hrn"))))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].amount", is(equalTo(200.0))))
                .andExpect(jsonPath("$[1].comment", is(equalTo("comment 2"))))
                .andExpect(jsonPath("$[1].currency", is(equalTo("usd"))));
    }

    @SneakyThrows
    @Test
    public void createOperation() {
        mvc.perform(post(OPERATIONS)
                            .contentType(APPLICATION_JSON)
                            .content("{\n" +
                                             "\t\"date\": \"2019-07-16T19:17:57.689Z\",\n" +
                                             "\t\"currency\": \"hrn\",\n" +
                                             "\t\"amount\": 200,\n" +
                                             "\t\"comment\": \"new operation\"\n" +
                                             "}")
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.amount", is(equalTo(200))))
                .andExpect(jsonPath("$.comment", is(equalTo("new operation"))))
                .andExpect(jsonPath("$.currency", is(equalTo("hrn"))));
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
        mvc.perform(get(OPERATIONS)
                            .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));
        mvc.perform(delete(OPERATIONS + "/" + newFirstOperation.getId())
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(delete(OPERATIONS + "/" + newSecondOperation.getId())
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get(OPERATIONS)
                            .accept(APPLICATION_JSON))
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

        mvc.perform(get(OPERATIONS)
                            .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        mvc.perform(put(OPERATIONS + "/" + newFirstOperation.getId())
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content("{\n" +
                                             "\t\"date\": \"2019-07-16T19:17:57.689Z\",\n" +
                                             "\t\"currency\": \"eur\",\n" +
                                             "\t\"amount\": 400,\n" +
                                             "\t\"comment\": \"update operation\"\n" +
                                             "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(equalTo(newFirstOperation.getId()))))
                .andExpect(jsonPath("$.amount", is(equalTo(400))))
                .andExpect(jsonPath("$.comment", is(equalTo("update operation"))))
                .andExpect(jsonPath("$.currency", is(equalTo("eur"))));
        operationRepository.delete(newFirstOperation);
    }
}