package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.ride.RideType;
import com.samir.uberweal.application.commands.BookRideCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.samir.uberweal.RideTestSetup.*;
import static org.junit.jupiter.api.Assertions.*;

class BookRideTest {

    private Rider rider;
    private BookRideCommandHandler underTest;

    @BeforeEach
    void setUp() {
        rider = setupRider();
        underTest = setupBookRideCommandHandler();
    }
    @ParameterizedTest(name = "{index}. {0} from {1} to {2}")
    @CsvSource({
            "TRIP, Paris, Outside Paris",
            "JOURNEY, Outside Paris, Paris",
            "JOURNEY, Paris, Paris",
            "TRIP, Outside Paris, Outside Paris",
    })
    @DisplayName("Book a Ride")
    void itShould_bookRide(
            RideType type,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        BookRideCommand rideCommand = buildBookRideCommand(rider, type, startPoint, destination, 0);
        double initialFunds = rider.getFunds();

        // Act
        underTest.handle(rideCommand);

        // Assert
        assertEquals(rider.getFunds(), initialFunds);
    }

}
