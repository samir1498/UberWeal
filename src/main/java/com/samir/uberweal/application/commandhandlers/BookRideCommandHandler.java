package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.application.commands.BookRideCommand;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.observers.RideCompletionObserver;
import com.samir.uberweal.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.domain.repositories.RideRepository;
import com.samir.uberweal.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.domain.services.pricing.calculator.RideChargeCalculator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class BookRideCommandHandler {

    private final RideRepository rideRepository;
    private final RideChargeCalculatorFactory rideChargeCalculatorFactory;

    public void handle(BookRideCommand command) {
        Ride ride = createRideFromCommand(command);
        double price = calculateExpectedCharge(ride);

        if (price == 0) {
            ride.getRider().setVoucher(true);
        }

        rideRepository.save(ride);
    }

    private Ride createRideFromCommand(BookRideCommand command) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl();

       Ride ride = Ride.builder()
               .rideType(command.type())
               .rider(command.rider())
               .startingPoint(command.riderLocation())
               .destination(command.destination())
               .distance(command.distance())
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

        applyDiscount(ride);

        return ride.getPrice();
    }

    private void applyDiscount(Ride ride){
        applyFirstYearDiscount(ride);
        applyDistanceDiscount(ride);
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
