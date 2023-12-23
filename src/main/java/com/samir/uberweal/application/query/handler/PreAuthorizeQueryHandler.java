package com.samir.uberweal.application.query.handler;

import com.samir.uberweal.application.query.queries.PreAuthorizeQuery;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.domain.gateways.RiderDsGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PreAuthorizeQueryHandler {
    private final RiderDsGateway repository;

    public Rider handle(PreAuthorizeQuery query) {
        Rider rider = repository.findRiderById(query.rider().getId());

        double updatedBalance = rider.getFunds() - query.amount();
        if (updatedBalance >= 0) {
            return rider;
        } else {
            throw new InsufficientFundsException("Insufficient funds to deduct: " + query.amount());
        }
    }
}
