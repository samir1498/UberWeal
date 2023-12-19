package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.ride.RideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RideChargeCalculatorFactory {
    private final Map<RideType, RideChargeCalculator> chargeCalculators;

    @Autowired
    public RideChargeCalculatorFactory(
            TripRideChargeCalculator tripRideChargeCalculator,
            JourneyRideChargeCalculator journeyRideChargeCalculator
    ) {
        chargeCalculators = Map.of(
                RideType.TRIP, tripRideChargeCalculator,
                RideType.JOURNEY, journeyRideChargeCalculator
        );
    }

    public RideChargeCalculator getRideChargeCalculator(RideType rideType) {
        return chargeCalculators.get(rideType);
    }
}
