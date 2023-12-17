package com.samir.uberweal.core.domain.entities.customer;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);
    Optional<Customer> findRiderById(Long Id);

}
