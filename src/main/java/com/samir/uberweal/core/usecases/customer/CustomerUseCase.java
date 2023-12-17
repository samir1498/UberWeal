package com.samir.uberweal.core.usecases.customer;

import com.samir.uberweal.core.domain.entities.customer.Customer;

public interface CustomerUseCase {

    Customer preAuthorize(Customer customer, double amount);

    Customer deductFunds(Customer customer, double amount);
}