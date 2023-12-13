package com.samir.uberweal.domain.customer;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);
    Optional<Customer> findRiderById(Long Id);

}
