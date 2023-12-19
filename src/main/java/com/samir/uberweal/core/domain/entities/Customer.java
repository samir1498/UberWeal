package com.samir.uberweal.core.domain.entities;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Customer {
    private Long id;
    private double funds;
    private LocalDate joinedAt;
    @Builder.Default
    private boolean voucher = false;

}

