package com.flax.finplat;

import com.flax.finplat.common.Calculator;
import com.flax.finplat.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.OffsetDateTime;

@EnableJpaRepositories("com.flax.finplat.repository")
@EntityScan("com.flax.finplat.model")
@SpringBootApplication
public class FinplatApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(FinplatApplication.class, args);
    }

    @Autowired
    private OperationService operationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        operationService.createOperation(OffsetDateTime.now(), "hrn", Calculator.normalize(200),"comment");
        operationService.createOperation(OffsetDateTime.now(), "usd", Calculator.normalize(200),"comment 2");
    }
}
