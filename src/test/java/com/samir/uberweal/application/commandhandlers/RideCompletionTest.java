package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.application.commands.BookRideCommand;
import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.entities.Driver;
import com.samir.uberweal.core.domain.entities.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideStatus;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.samir.uberweal.BookRideTestSetup.*;
import static com.samir.uberweal.BookRideTestSetup.setupBookRideCommandHandler;
import static org.junit.jupiter.api.Assertions.*;

class RideCompletionTest {
    private Rider rider;
    private Driver driver;
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
            RideType type,
            Location startPoint,
            Location destination,
            double expectedCharge
    ) {
        // Arrange
        double initialFunds = rider.getFunds();
        BookRideCommand bookRideCommand = buildBookRideCommand(rider, type, startPoint, destination, 0);
        underTest.handle(bookRideCommand);

        // Act
        Ride ride = rideRepository.findAll().get(0);
        ride.completeRide();

        // Assert
        double finalFunds = rider.getFunds();

        assertEquals(initialFunds - expectedCharge, finalFunds);
        assertEquals(RideStatus.COMPLETED, ride.getStatus());
    }


}