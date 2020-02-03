package com.flax.finplat.controller;

import com.flax.finplat.model.Operation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MyOperationControllerTest {
    private static final String API_ROOT
            = "http://localhost:7070/api/my/operations";

//    private Operation createRandomOperation(){
//        return Operation.createEmpty(OffsetDateTime.now(), "USD", BigDecimal.ONE,"Random comment");
//    }
//
//    private String createOperationAsUri(Operation operation) {
//        Response response = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(book)
//                .post(API_ROOT);
//        return API_ROOT + "/" + response.jsonPath().get("id");
//    }
    @Test
    void viewMyOperations() {

    }

    @Test
    void createOperation() {
    }

    @Test
    void deleteOperation() {
    }

    @Test
    void updateOperation() {
    }
}