package com.samir.uberweal.core.domain.services.pricing;

import com.samir.uberweal.core.domain.entities.ride.RideType;
import com.samir.uberweal.core.domain.services.pricing.calculator.RideChargeCalculator;
import com.samir.uberweal.core.domain.services.pricing.strategies.JourneyRideChargeCalculator;
import com.samir.uberweal.core.domain.services.pricing.strategies.TripRideChargeCalculator;
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
