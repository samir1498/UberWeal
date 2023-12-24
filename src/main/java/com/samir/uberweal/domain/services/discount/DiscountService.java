package com.samir.uberweal.domain.services.discount;

import com.samir.uberweal.domain.entities.BookRide;

import java.time.LocalDate;

public class DiscountService {

    public void applyDistanceDiscount(BookRide ride) {
        double distance = ride.getDistance();
        double price = ride.getPrice();

        if (distance < 5 && distance > 0) {
            ride.setPrice(price - 5);
        }
    }
    public void applyFirstYearDiscount(BookRide ride) {
        LocalDate joinedAt = ride.getRider().getJoinedAt();
        LocalDate today = LocalDate.now();
        double price = ride.getPrice();
        if (joinedAt.plusYears(1).isBefore(today)) {
            ride.setPrice(price / 2);
        }
    }
}
