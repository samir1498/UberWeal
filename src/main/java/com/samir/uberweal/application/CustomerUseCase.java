package com.samir.uberweal.application;

import com.samir.uberweal.core.domain.entities.Customer;

public interface CustomerUseCase {

    Customer preAuthorize(Customer customer, double amount);

    void deductFunds(Customer customer, double amount);
}