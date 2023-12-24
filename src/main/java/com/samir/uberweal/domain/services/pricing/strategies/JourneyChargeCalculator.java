package com.samir.uberweal.domain.services.pricing.strategies;

import com.samir.uberweal.domain.entities.BookRide;
import org.springframework.stereotype.Component;

@Component
public class JourneyChargeCalculator implements RideChargeCalculator {
    @Override
    public double calculateCharge(BookRide ride) {
        boolean isRiderInParis = ride.getStartLocation().getName().equals("Paris");
        boolean isDestinationParis = ride.getEndLocation().getName().equals("Paris");

        return (!isRiderInParis && isDestinationParis) ? 0 :
               (isRiderInParis && isDestinationParis) ? 10 : -1;
    }
}
