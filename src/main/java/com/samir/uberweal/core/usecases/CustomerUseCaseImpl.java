package com.samir.uberweal.core.usecases;


import com.samir.uberweal.application.CustomerUseCase;
import com.samir.uberweal.core.domain.entities.Customer;
import com.samir.uberweal.core.domain.exceptions.CustomerNotFoundException;
import com.samir.uberweal.core.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.core.domain.repositories.CustomerRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CustomerUseCaseImpl implements CustomerUseCase {

    private final CustomerRepository repository;

    @Override
    public Customer preAuthorize(Customer customer, double amount) {
        Optional<Customer> riderOptional = repository.findRiderById(customer.getId());

        if (riderOptional.isPresent()) {
            double updatedBalance = riderOptional.get().getFunds() - amount;
            if (updatedBalance >= 0) {
                return riderOptional.get();
            } else {
                throw new InsufficientFundsException("Insufficient funds to deduct: " + amount);
            }
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + customer.getId());
        }
    }

    @Override
    public void deductFunds(Customer customer, double amount) {
        double funds = customer.getFunds();
        customer.setFunds(funds - amount);
        repository.save(customer);
    }

}


