package com.samir.uberweal.domain.services.pricing;

import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.domain.services.pricing.strategies.RideChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.JourneyChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.TripChargeCalculator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class RideChargeCalculatorFactory {
    private final Map<BookRide.RideType, RideChargeCalculator> chargeCalculators;
    @Autowired
    public RideChargeCalculatorFactory(
            TripChargeCalculator tripChargeCalculator,
            JourneyChargeCalculator journeyChargeCalculator
    ) {
        chargeCalculators = Map.of(
                BookRide.RideType.TRIP, tripChargeCalculator,
                BookRide.RideType.JOURNEY, journeyChargeCalculator
        );
    }

    public RideChargeCalculator getRideChargeCalculator(BookRide.RideType rideType) {
        return chargeCalculators.get(rideType);
    }
}
