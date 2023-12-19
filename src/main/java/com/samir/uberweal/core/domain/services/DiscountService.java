package com.samir.uberweal.core.domain.services;

import com.samir.uberweal.core.domain.entities.ride.Ride;

public interface DiscountService {
    void applyDistanceDiscount(Ride ride);

    void applyFirstYearDiscount(Ride ride);

}
