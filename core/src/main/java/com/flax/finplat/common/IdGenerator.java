package com.flax.finplat.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Unique id generator. Use it when you create the new entity, when you want to generate unique ID
 */
@Component
public class IdGenerator {

    public String nextId() {
        return UUID.randomUUID().toString();
    }
}
