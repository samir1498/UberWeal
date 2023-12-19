package com.samir.uberweal.application;

import com.samir.uberweal.core.domain.entities.ride.Ride;

public interface BookRideUseCase {
    Ride bookRide(Ride ride);

    double calculateExpectedCharge(Ride ride);
}
