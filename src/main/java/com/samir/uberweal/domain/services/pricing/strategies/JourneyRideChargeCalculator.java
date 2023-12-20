package com.samir.uberweal.domain.services.pricing.strategies;

import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.services.pricing.calculator.RideChargeCalculator;
import org.springframework.stereotype.Component;

@Component
public class JourneyRideChargeCalculator implements RideChargeCalculator {
    @Override
    public double calculateCharge(Ride ride) {
        boolean isRiderInParis = ride.getStartingPoint().getName().equals("Paris");
        boolean isDestinationParis = ride.getDestination().getName().equals("Paris");

        return (!isRiderInParis && isDestinationParis) ? 0 :
               (isRiderInParis && isDestinationParis) ? 10 : -1;
    }
}
