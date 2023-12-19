package com.samir.uberweal.core.domain.entities.driver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Driver {
    private final Long id;
    private final String name;


}

