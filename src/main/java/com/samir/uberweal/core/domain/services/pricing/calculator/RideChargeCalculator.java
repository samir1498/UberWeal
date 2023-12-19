package com.samir.uberweal.core.domain.services.pricing.calculator;

import com.samir.uberweal.core.domain.entities.ride.Ride;


public interface RideChargeCalculator {
    double calculateCharge(Ride ride);
}
