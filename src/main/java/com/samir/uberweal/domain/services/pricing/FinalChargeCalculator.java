package com.samir.uberweal.domain.services.pricing;

import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.domain.services.discount.DiscountService;
import com.samir.uberweal.domain.services.pricing.strategies.RideChargeCalculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FinalChargeCalculator {
    private final DiscountService discountService;
    private final  RideChargeCalculatorFactory rideChargeCalculatorFactory;

    public double calculateExpectedCharge(BookRide ride) {
        RideChargeCalculator chargeCalculator = rideChargeCalculatorFactory.
                getRideChargeCalculator(ride.getRideType());

        if (chargeCalculator == null) {
            throw new IllegalArgumentException("Unsupported ride type");
        }

        double baseCharge = chargeCalculator.calculateCharge(ride);

        ride.setPrice(baseCharge);

        discountService.applyFirstYearDiscount(ride);
        discountService.applyDistanceDiscount(ride);

        return ride.getPrice();
    }
}
