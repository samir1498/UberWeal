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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RideBookingTest {

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

    @ParameterizedTest(name = "{index}. {0} from {1} to {2}")
    @CsvSource({
            "TRIP, Paris, Outside Paris",
            "JOURNEY, Outside Paris, Paris",
            "JOURNEY, Paris, Paris",
            "TRIP, Outside Paris, Outside Paris",
    })
    @DisplayName("Book a Ride When Customer has Sufficient Funds")
    void itShould_bookRide_WithSufficientFunds(
            RideType type,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination);
        double initialFunds = setupResult.customer.getFunds();

        // Act
        Ride bookedRide = setupResult.underTest.bookRide(ride);

        // Assert
        assertNotNull(bookedRide);
        assertEquals(setupResult.customer.getFunds(), initialFunds);

    }



}
