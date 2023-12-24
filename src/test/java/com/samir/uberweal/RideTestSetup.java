package com.samir.uberweal;

import com.samir.uberweal.application.command.handlers.BookRideCommandHandler;
import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.application.query.handler.GetAllRidesQueryHandler;
import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.adapters.gateways.BookRideDsGateway;
import com.samir.uberweal.adapters.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.adapters.gateways.stubs.BookRideDsGatewayStub;
import com.samir.uberweal.adapters.gateways.stubs.ListPastRidesDsGatewayStub;
import com.samir.uberweal.domain.services.discount.DiscountService;
import com.samir.uberweal.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.domain.services.pricing.FinalChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.JourneyChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.TripChargeCalculator;
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
        return new BookRideCommandHandler(BOOK_RIDE_DS_GATEWAY, setupPriceCalculator(setupCalculatorFactory()));
    }

    private static RideChargeCalculatorFactory setupCalculatorFactory() {
        TripChargeCalculator tripChargeCalculator = new TripChargeCalculator();
        JourneyChargeCalculator journeyChargeCalculator = new JourneyChargeCalculator();

        return new RideChargeCalculatorFactory(tripChargeCalculator, journeyChargeCalculator);
    }

    private static FinalChargeCalculator setupPriceCalculator(RideChargeCalculatorFactory rideChargeCalculatorFactory) {
        DiscountService discountService = new DiscountService();
        return new FinalChargeCalculator(discountService, rideChargeCalculatorFactory);
    }

    public static BookRideCommand buildBookRideCommand(
            Rider rider,
            BookRide.RideType type,
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
