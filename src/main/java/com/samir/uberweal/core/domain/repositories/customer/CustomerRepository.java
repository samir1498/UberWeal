package com.samir.uberweal.core.domain.repositories.customer;

import com.samir.uberweal.core.domain.entities.customer.Customer;

import java.util.Optional;

public interface CustomerRepository {

    void save(Customer customer);
    Optional<Customer> findRiderById(Long Id);

}
