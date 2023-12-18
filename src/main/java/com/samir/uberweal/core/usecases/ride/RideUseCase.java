package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.ride.Ride;

public interface RideUseCase {
    Ride bookRide(Ride ride);
    double calculateExpectedCharge(Ride ride);


}

