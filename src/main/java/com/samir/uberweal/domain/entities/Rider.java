package com.samir.uberweal.domain.entities;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Rider {
    private Long id;
    private String name;
    private double funds;
    private LocalDate joinedAt;
    @Builder.Default
    private boolean voucher = false;

}

