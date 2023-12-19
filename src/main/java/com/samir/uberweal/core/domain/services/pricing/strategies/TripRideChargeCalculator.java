package com.samir.uberweal.core.domain.services.pricing.strategies;

import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.services.pricing.calculator.RideChargeCalculator;
import org.springframework.stereotype.Component;

@Component
public class TripRideChargeCalculator implements RideChargeCalculator {
    @Override
    public double calculateCharge(Ride ride) {
        boolean isRiderInParis = ride.getStartingPoint().getName().equals("Paris");
        boolean isDestinationParis = ride.getDestination().getName().equals("Paris");

        return (isRiderInParis && !isDestinationParis) ? 30 :
               (!isRiderInParis && !isDestinationParis) ? 50 : -1;
    }
}