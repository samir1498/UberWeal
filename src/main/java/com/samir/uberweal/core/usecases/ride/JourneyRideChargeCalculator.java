package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.ride.Ride;
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
