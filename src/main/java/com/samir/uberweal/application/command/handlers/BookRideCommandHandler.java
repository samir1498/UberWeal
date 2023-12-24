package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.adapters.gateways.BookRideDsGateway;
import com.samir.uberweal.domain.observers.RideCompletionObserver;
import com.samir.uberweal.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.domain.services.pricing.FinalChargeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.samir.uberweal.adapters.dtos.RideDto.RideToRideDto;

@RequiredArgsConstructor
@Service
public class BookRideCommandHandler {

    private final BookRideDsGateway bookRideDsGateway;
    private final FinalChargeCalculator calculator;

    public RideDto handle(BookRideCommand command) {
        BookRide ride = createRideFromCommand(command);
        double price = calculator.calculateExpectedCharge(ride);

        if (price == 0) {
            ride.getRider().setVoucher(true);
        }

        bookRideDsGateway.save(ride);
        return RideToRideDto(ride);
    }

    private BookRide createRideFromCommand(BookRideCommand command) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl();
        BookRide ride = commandToRide(command);
        ride.addObserver(completionObserver);
        return ride;
    }

    private static BookRide commandToRide(BookRideCommand command) {
        return BookRide.builder()
                .rideType(command.type())
                .rider(command.rider())
                .startLocation(command.startLocation())
                .endLocation(command.endLocation())
                .distance(command.distance())
                .status(BookRide.RideStatus.IN_PROGRESS)
                .build();
    }


}
