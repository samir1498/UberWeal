package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.application.command.commands.DeductFundsCommand;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.adapters.gateways.RiderDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeductFundsCommandHandler {


    private final RiderDsGateway repository;

    public void handle(DeductFundsCommand command) {
        Rider rider = repository.findRiderById(command.riderId());

        double updatedBalance = rider.getFunds() - command.amount();

        if (updatedBalance >= 0) {
            rider.setFunds(updatedBalance);
            repository.save(rider);
        } else {
            throw new InsufficientFundsException("Insufficient funds to deduct: " + command.amount());
        }
    }
}
