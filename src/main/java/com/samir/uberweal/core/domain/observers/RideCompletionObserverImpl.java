package com.samir.uberweal.core.domain.observers;

import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.usecases.customer.CustomerUseCase;
import com.samir.uberweal.core.usecases.ride.RideUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RideCompletionObserverImpl implements RideCompletionObserver {

    private final RideUseCase rideUseCase;
    private final CustomerUseCase customerUseCase;
    @Override
    public void rideCompleted(Ride ride) {
        // Charge the Rider's account
        customerUseCase.deductFunds(ride.getCustomer(), ride.getPrice());
        // Update ride history
       // updateRideHistory(ride);
    }


}
