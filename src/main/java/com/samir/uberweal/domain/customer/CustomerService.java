package com.samir.uberweal.domain.customer;

public interface CustomerService {

    Customer preAuthorize(Customer customer, double amount);

    Customer deductFunds(Customer customer, double amount);
}