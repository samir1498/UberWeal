package com.samir.uberweal.domain.services.pricing.strategies;

import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.services.pricing.calculator.RideChargeCalculator;
import org.springframework.stereotype.Component;

@Component
public class JourneyRideChargeCalculator implements RideChargeCalculator {
    @Override
    public double calculateCharge(Ride ride) {
        boolean isRiderInParis = ride.getStartLocation().getName().equals("Paris");
        boolean isDestinationParis = ride.getEndLocation().getName().equals("Paris");

        return (!isRiderInParis && isDestinationParis) ? 0 :
               (isRiderInParis && isDestinationParis) ? 10 : -1;
    }
}
