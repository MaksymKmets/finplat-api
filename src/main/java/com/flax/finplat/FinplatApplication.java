package com.flax.finplat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.flax.finplat.repository")
@EntityScan("com.flax.finplat.model")
@SpringBootApplication
public class FinplatApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinplatApplication.class, args);
    }

}
