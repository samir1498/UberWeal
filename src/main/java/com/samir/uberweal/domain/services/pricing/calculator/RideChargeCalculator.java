package com.samir.uberweal.domain.services.pricing.calculator;

import com.samir.uberweal.domain.entities.ride.Ride;


public interface RideChargeCalculator {
    double calculateCharge(Ride ride);
}
