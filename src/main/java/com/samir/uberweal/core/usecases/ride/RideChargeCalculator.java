package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.ride.Ride;


public interface RideChargeCalculator {
    double calculateCharge(Ride ride);
}
