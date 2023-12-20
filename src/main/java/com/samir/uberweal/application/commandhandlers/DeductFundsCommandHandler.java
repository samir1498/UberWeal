package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.application.commands.DeductFundsCommand;
import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.core.domain.exceptions.RiderNotFoundException;
import com.samir.uberweal.core.domain.repositories.RiderRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeductFundsCommandHandler {

    private final RiderRepository repository;

    public void handle(DeductFundsCommand command) {
        Rider rider = repository.findRiderById(command.riderId())
                .orElseThrow(() -> new RiderNotFoundException("Rider not found with ID: " + command.riderId()));

        double updatedBalance = rider.getFunds() - command.amount();

        if (updatedBalance >= 0) {
            rider.setFunds(updatedBalance);
            repository.save(rider);
        } else {
            throw new InsufficientFundsException("Insufficient funds to deduct: " + command.amount());
        }
    }
}
