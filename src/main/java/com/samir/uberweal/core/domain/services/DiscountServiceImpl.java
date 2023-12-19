package com.samir.uberweal.core.domain.services;

import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.time.LocalDate;

public class DiscountServiceImpl implements DiscountService {
    public void applyDistanceDiscount(Ride ride) {
        double distance = ride.getDistance();
        double price = ride.getPrice();

        if (distance < 5 && distance > 0) {
            ride.setPrice(price - 5);
        }
    }

    public void applyFirstYearDiscount(Ride ride) {
        LocalDate joinedAt = ride.getCustomer().getJoinedAt();
        LocalDate today = LocalDate.now();
        double price = ride.getPrice();
        if (joinedAt.plusYears(1).isBefore(today)) {
            ride.setPrice(price / 2);
        }
    }

}
