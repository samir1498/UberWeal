package com.samir.uberweal.domain.observers;

import com.samir.uberweal.domain.entities.ride.Ride;

public interface RideCompletionObserver {
    void rideCompleted(Ride ride);
}
