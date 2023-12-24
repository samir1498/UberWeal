package com.samir.uberweal.domain.services.pricing.strategies;

import com.samir.uberweal.domain.entities.BookRide;


public interface RideChargeCalculator {
    double calculateCharge(BookRide ride);
}
