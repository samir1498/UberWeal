package com.samir.uberweal.core.domain.entities.customer;


import java.time.LocalDate;
import java.util.Optional;

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

    public boolean hasSufficientFunds(double amount){
        return (this.funds - amount) >= 0;
    }

    public static interface CustomerRepository {

        Customer save(Customer customer);
        Optional<Customer> findRiderById(Long Id);

    }
}

