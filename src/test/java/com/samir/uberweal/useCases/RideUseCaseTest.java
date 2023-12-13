package com.samir.uberweal.useCases;

import com.samir.uberweal.core.domain.ride.Location;
import com.samir.uberweal.core.domain.ride.Ride;
import com.samir.uberweal.core.domain.ride.RideType;
import com.samir.uberweal.core.domain.driver.*;
import com.samir.uberweal.core.domain.rider.Rider;
import com.samir.uberweal.core.useCases.RideUseCase;
import com.samir.uberweal.core.useCases.RideUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RideUseCaseTest {

    private RideUseCase underTest;

    private Rider rider;
    private Driver driver;

    @BeforeEach
    void setUp() {
        underTest = new RideUseCaseImpl();

        rider = new Rider("riderId", 100.0);
        driver = new Driver("driverId", "DriverName");
    }
    @ParameterizedTest(name = "{index}. {0} from {2} to {3} costs {1}€")
    @CsvSource({
            "TRIP, 30, Paris, Outside Paris",
            "JOURNEY, 0, Outside Paris, Paris",
            "JOURNEY, 10, Paris, Paris",
            "TRIP, 50, Outside Paris, Outside Paris",
    })
    @DisplayName("It Should Apply Correct Price")
    void itShould_bookDriver_ShouldApplyCorrectPrice(
            RideType rideType,
            double expectedPrice,
            String startingPoint,
            String destination
    ) {
        // Arrange
        Location startPoint = new Location(startingPoint);
        Location destinationPoint = new Location(destination);

        // Act
        Ride bookedRide = underTest.bookRide(rider, driver, destinationPoint, startPoint, rideType);

        // Assert
        assertEquals(expectedPrice, bookedRide.getPrice());
        assertEquals(rideType, bookedRide.getRideType());
    }


    @ParameterizedTest
    @CsvSource({
            "TRIP, Paris, Outside Paris",
    })
    void bookDriver_WithSufficientFunds_ShouldPerformPreAuthorizationAndBookRide(
            RideType type,
            String startPoint,
            String destinationPoint
    ) {
        // Arrange
        Location startingPoint = new Location(startPoint);
        Location destination = new Location(destinationPoint);

        // Act
        Ride bookedRide = underTest.bookRide(rider, driver, destination, startingPoint, type);

        // Assert
        assertNotNull(bookedRide);
        assertEquals(rider.getFunds(), 70.0); // Assuming the ride price is 30.0

    }

}
