package com.samir.uberweal.core.domain.repositories.stubs;

import com.samir.uberweal.core.domain.entities.Customer;
import com.samir.uberweal.core.domain.repositories.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepositoryStub implements CustomerRepository {

    private final Map<Long, Customer> riderDatabase = new HashMap<>();
    private long currentId = 1;

    @Override
    public void save(Customer customer) {
        // Assign an ID if the Rider is new
        if (customer.getId() == null) {
            customer.setId(currentId++);
        }

        // Save or update the Rider in the in-memory database
        riderDatabase.put(customer.getId(), customer);

    }

    @Override
    public Optional<Customer> findRiderById(Long id) {
        // Retrieve a Rider from the in-memory database by ID
        return Optional.ofNullable(riderDatabase.get(id));
    }
}
