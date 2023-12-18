package com.samir.uberweal.core.domain.observers;

import com.samir.uberweal.core.domain.entities.ride.Ride;

public interface RideCompletionObserver {
    void rideCompleted(Ride ride);
}
