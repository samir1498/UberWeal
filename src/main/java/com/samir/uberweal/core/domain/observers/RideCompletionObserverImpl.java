package com.samir.uberweal.core.domain.observers;

import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RideCompletionObserverImpl implements RideCompletionObserver {

    @Override
    public void rideCompleted(Ride ride) {
        Rider rider = ride.getRider();
        double price = ride.getPrice();
        double initialFunds = rider.getFunds();
        double finalFunds = initialFunds - price;
        rider.setFunds(finalFunds);
    }


}
