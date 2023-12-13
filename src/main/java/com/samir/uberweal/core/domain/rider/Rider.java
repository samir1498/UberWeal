package com.samir.uberweal.core.domain.rider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Rider {
    private final String id;
    private double funds;

}

