package com.samir.uberweal.core.domain.repositories;

import com.samir.uberweal.core.domain.entities.Customer;

import java.util.Optional;

public interface CustomerRepository {

    void save(Customer customer);
    Optional<Customer> findRiderById(Long Id);

}
