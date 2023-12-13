package com.samir.uberweal.domain.customer;


import com.samir.uberweal.domain.ride.CustomerNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryStub repositoryStub;

    @Override
    public Customer preAuthorize(Customer customer, double amount) {
        Optional<Customer> riderOptional = repositoryStub.findRiderById(customer.getId());

        if (riderOptional.isPresent()) {
            double updatedBalance = riderOptional.get().getFunds() - amount;
            if (updatedBalance >= 0) {
                return riderOptional.get();
            } else {
                throw new InsufficientFundsException("Insufficient funds to deduct: " + amount);
            }
        } else {
            throw new CustomerNotFoundException("Rider not found with ID: " + customer.getId());
        }
    }

    @Override
    public Customer deductFunds(Customer customer, double amount) {
            double funds = customer.getFunds();
            customer.setFunds(funds - amount);
            repositoryStub.save(customer);
            return customer;
    }

}


