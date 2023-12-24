package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.BookRide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.samir.uberweal.RideTestSetup.*;
import static org.junit.jupiter.api.Assertions.*;

class RideCompletionTest {
    private Rider rider;
    private BookRideCommandHandler underTest;

    @BeforeEach
    void setUp() {
        rider = setupRider();
        underTest = setupBookRideCommandHandler();
    }
    @ParameterizedTest(name = "{index}. {0} from {1} to {2} Should Charge {3} after Ride Completed")
    @CsvSource({
            "TRIP, Paris, Outside Paris, 30",
            "JOURNEY, Outside Paris, Paris, 0",
            "JOURNEY, Paris, Paris, 10",
            "TRIP, Outside Paris, Outside Paris, 50",
    })
    @DisplayName("It Should Charge rider When Ride Is Complete")
    void itShould_ChargeRider_WhenRideIsComplete(
            BookRide.RideType type,
            Location startPoint,
            Location destination,
            double expectedCharge
    ) {
        // Arrange
        double initialFunds = rider.getFunds();
        BookRideCommand bookRideCommand = buildBookRideCommand(rider, type, startPoint, destination, 0);
        underTest.handle(bookRideCommand);

        // Act
        BookRide ride = LIST_PAST_RIDES_DS_GATEWAY.findByRiderId(rider.getId()).get(0);
        ride.completeRide();

        // Assert
        double finalFunds = rider.getFunds();

        assertEquals(initialFunds - expectedCharge, finalFunds);
        assertEquals(BookRide.RideStatus.COMPLETED, ride.getStatus());
    }


}