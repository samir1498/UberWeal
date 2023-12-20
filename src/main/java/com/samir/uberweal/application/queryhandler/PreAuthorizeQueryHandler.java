package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.core.domain.exceptions.RiderNotFoundException;
import com.samir.uberweal.core.domain.repositories.RiderRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PreAuthorizeQueryHandler {
    private final RiderRepository repository;

    public Rider handle(Rider Rider, double amount) {
        Optional<Rider> riderOptional = repository.findRiderById(Rider.getId());

        if (riderOptional.isPresent()) {
            double updatedBalance = riderOptional.get().getFunds() - amount;
            if (updatedBalance >= 0) {
                return riderOptional.get();
            } else {
                throw new InsufficientFundsException("Insufficient funds to deduct: " + amount);
            }
        } else {
            throw new RiderNotFoundException("Rider not found with ID: " + Rider.getId());
        }
    }
}
