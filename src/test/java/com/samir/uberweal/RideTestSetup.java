package com.samir.uberweal;

import com.samir.uberweal.application.command.handlers.BookRideCommandHandler;
import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.application.query.handler.GetAllRidesQueryHandler;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.ride.RideType;
import com.samir.uberweal.domain.gateways.BookRideDsGateway;
import com.samir.uberweal.domain.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.domain.gateways.stubs.BookRideDsGatewayStub;
import com.samir.uberweal.domain.gateways.stubs.ListPastRidesDsGatewayStub;
import com.samir.uberweal.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.domain.services.pricing.strategies.JourneyRideChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.TripRideChargeCalculator;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RideTestSetup {

    public final static BookRideDsGateway BOOK_RIDE_DS_GATEWAY = new BookRideDsGatewayStub();
    public final static ListPastRidesDsGateway LIST_PAST_RIDES_DS_GATEWAY = new ListPastRidesDsGatewayStub();

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

        return new BookRideCommandHandler(BOOK_RIDE_DS_GATEWAY, rideChargeCalculatorFactory);
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
                .startLocation(startPoint)
                .endLocation(destination)
                .distance(distance)
                .build();
    }
    public static GetAllRidesQueryHandler setupRideQueryHandler() {
        return new GetAllRidesQueryHandler(LIST_PAST_RIDES_DS_GATEWAY);
    }


}
