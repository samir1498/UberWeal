package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.application.commands.BookRideCommand;
import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.entities.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.samir.uberweal.BookRideTestSetup.*;
import static com.samir.uberweal.BookRideTestSetup.setupBookRideCommandHandler;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RidePricingTest {

    private Rider rider;
    private BookRideCommandHandler underTest;

    @BeforeEach
    void setUp() {
        rider = setupRider();
        underTest = setupBookRideCommandHandler();
    }


    @ParameterizedTest(name = "{index}. {0} from {2} to {3} costs {1}€")
    @CsvSource({
            "TRIP, 30, Paris, Outside Paris",
            "JOURNEY, 0, Outside Paris, Paris",
            "JOURNEY, 10, Paris, Paris",
            "TRIP, 50, Outside Paris, Outside Paris",
    })
    @DisplayName("It Should Apply Correct Price")

    void itShould_ApplyCorrectPrice(
            RideType type,
            double expectedPrice,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        BookRideCommand bookRideCommand = buildBookRideCommand(rider, type, startPoint, destination, 0);

        // Act
        underTest.handle(bookRideCommand);
        Ride bookedRide = rideRepository.findAll().get(0);

        // Assert
        assertEquals(expectedPrice, bookedRide.getPrice());
        assertEquals(type, bookedRide.getRideType());
    }


}
