package com.samir.uberweal.domain.observers;

import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.BookRide;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RideCompletionObserverImpl implements RideCompletionObserver {

    @Override
    public void rideCompleted(BookRide ride) {
        Rider rider = ride.getRider();
        double price = ride.getPrice();
        double initialFunds = rider.getFunds();
        double finalFunds = initialFunds - price;
        rider.setFunds(finalFunds);
    }


}
