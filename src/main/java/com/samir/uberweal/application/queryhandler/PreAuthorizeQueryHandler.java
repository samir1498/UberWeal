package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.application.queries.PreAuthorizeQuery;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.domain.exceptions.RiderNotFoundException;
import com.samir.uberweal.domain.repositories.RiderRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PreAuthorizeQueryHandler {
    private final RiderRepository repository;

    public Rider handle(PreAuthorizeQuery query) {
        Optional<Rider> riderOptional = repository.findRiderById(query.rider().getId());

        if (riderOptional.isPresent()) {
            double updatedBalance = riderOptional.get().getFunds() - query.amount();
            if (updatedBalance >= 0) {
                return riderOptional.get();
            } else {
                throw new InsufficientFundsException("Insufficient funds to deduct: " + query.amount());
            }
        } else {
            throw new RiderNotFoundException("Rider not found with ID: " + query.rider().getId());
        }
    }
}
