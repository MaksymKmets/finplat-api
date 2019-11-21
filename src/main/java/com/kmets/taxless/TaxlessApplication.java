package com.kmets.taxless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class TaxlessApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxlessApplication.class, args);
    }

}
