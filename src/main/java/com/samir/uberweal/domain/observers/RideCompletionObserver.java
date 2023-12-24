package com.samir.uberweal.domain.observers;

import com.samir.uberweal.domain.entities.BookRide;

public interface RideCompletionObserver {
    void rideCompleted(BookRide ride);
}
