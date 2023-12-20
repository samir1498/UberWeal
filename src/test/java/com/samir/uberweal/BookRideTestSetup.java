package com.samir.uberweal;

import com.samir.uberweal.application.commands.BookRideCommand;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.ride.RideType;
import com.samir.uberweal.domain.repositories.RideRepository;
import com.samir.uberweal.domain.repositories.stubs.RideRepositoryStub;
import com.samir.uberweal.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.domain.services.pricing.strategies.JourneyRideChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.TripRideChargeCalculator;
import com.samir.uberweal.application.commandhandlers.BookRideCommandHandler;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookRideTestSetup {

    public final static RideRepository rideRepository = new RideRepositoryStub();

    public static Rider setupRider() {
        return Rider.builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
    }


    public static BookRideCommandHandler setupBookRideCommandHandler() {
        TripRideChargeCalculator tripRideChargeCalculator = new TripRideChargeCalculator();
        JourneyRideChargeCalculator journeyRideChargeCalculator = new JourneyRideChargeCalculator();
        RideChargeCalculatorFactory rideChargeCalculatorFactory = new RideChargeCalculatorFactory(tripRideChargeCalculator, journeyRideChargeCalculator);

        return new BookRideCommandHandler(rideRepository, rideChargeCalculatorFactory);
    }

    public static BookRideCommand buildBookRideCommand(
            Rider rider,
            RideType type,
            Location startPoint,
            Location destination,
            double distance
    ) {
        return BookRideCommand.builder()
                .type(type)
                .rider(rider)
                .riderLocation(startPoint)
                .destination(destination)
                .distance(distance)
                .build();
    }
}
