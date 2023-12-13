package com.samir.uberweal.core.domain.driver;

import lombok.Data;

@Data
public class Driver {
    private final String id;
    private final String name;

    public Driver(String id, String name) {
        this.id = id;
        this.name = name;
    }

}

