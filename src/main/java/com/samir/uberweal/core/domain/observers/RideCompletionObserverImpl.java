package com.samir.uberweal.core.domain.observers;

import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.application.CustomerUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RideCompletionObserverImpl implements RideCompletionObserver {

    private final CustomerUseCase customerUseCase;
    @Override
    public void rideCompleted(Ride ride) {
        customerUseCase.deductFunds(ride.getCustomer(), ride.getPrice());
    }


}
