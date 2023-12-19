package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.location.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideStatus;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RidePricingTest {

    private RideTestUtils.SetupResult setupResult;

    @BeforeEach
    void setUp() {
        setupResult = RideTestUtils.commonSetup();
    }


    @AfterEach
    void tearDown() {
        setupResult.customer.setFunds(100);
    }

    private Ride buildRide(RideType type, Location startPoint, Location destination) {
        Ride ride =  Ride.builder()
                .customer(setupResult.customer)
                .driver(setupResult.driver)
                .destination(destination)
                .startingPoint(startPoint)
                .rideType(type)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(setupResult.completionObserver);
        return ride;
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
        Ride ride = buildRide(type, startPoint, destination);

        // Act
        Ride bookedRide = setupResult.underTest.bookRide(ride);

        // Assert
        assertEquals(expectedPrice, bookedRide.getPrice());
        assertEquals(type, bookedRide.getRideType());
    }


}
