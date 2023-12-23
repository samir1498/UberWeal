package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.entities.ride.RideStatus;
import com.samir.uberweal.domain.observers.RideCompletionObserver;
import com.samir.uberweal.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.domain.gateways.BookRideDsGateway;
import com.samir.uberweal.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.domain.services.pricing.calculator.RideChargeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.samir.uberweal.adapters.dtos.ReMappers.RideToRideDto;

@RequiredArgsConstructor
@Service
public class BookRideCommandHandler {

    private final BookRideDsGateway bookRideDsGateway;
    private final RideChargeCalculatorFactory rideChargeCalculatorFactory;

    public RideDto handle(BookRideCommand command) {
        Ride ride = createRideFromCommand(command);
        double price = calculateExpectedCharge(ride);

        if (price == 0) {
            ride.getRider().setVoucher(true);
        }

        bookRideDsGateway.save(ride);
        return RideToRideDto(ride);
    }

    private Ride createRideFromCommand(BookRideCommand command) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl();

       Ride ride = Ride.builder()
               .rideType(command.type())
               .rider(command.rider())
               .startLocation(command.startLocation())
               .endLocation(command.endLocation())
               .distance(command.distance())
               .status(RideStatus.IN_PROGRESS)
               .build();
       ride.addObserver(completionObserver);
       return ride;
    }

    private double calculateExpectedCharge(Ride ride) {
        RideChargeCalculator chargeCalculator = rideChargeCalculatorFactory.
                getRideChargeCalculator(ride.getRideType());

        if (chargeCalculator == null) {
            throw new IllegalArgumentException("Unsupported ride type");
        }

        double baseCharge = chargeCalculator.calculateCharge(ride);

        ride.setPrice(baseCharge);

        applyFirstYearDiscount(ride);
        applyDistanceDiscount(ride);

        return ride.getPrice();
    }

    private void applyDistanceDiscount(Ride ride) {
        double distance = ride.getDistance();
        double price = ride.getPrice();

        if (distance < 5 && distance > 0) {
            ride.setPrice(price - 5);
        }
    }
    private void applyFirstYearDiscount(Ride ride) {
        LocalDate joinedAt = ride.getRider().getJoinedAt();
        LocalDate today = LocalDate.now();
        double price = ride.getPrice();
        if (joinedAt.plusYears(1).isBefore(today)) {
            ride.setPrice(price / 2);
        }
    }
}
