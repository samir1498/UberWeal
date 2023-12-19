package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.location.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideStatus;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RideBookingHistoryTest {

    private RideTestUtils.SetupResult setupResult;

    @BeforeEach
    void setUp() {
        setupResult = RideTestUtils.commonSetup();
    }


    private Ride buildRide(Long id, RideType type, String startPoint, String destination) {
        Ride ride =  Ride.builder()
                .id(id)
                .customer(setupResult.customer)
                .driver(setupResult.driver)
                .destination(new Location(destination))
                .startingPoint(new Location(startPoint))
                .rideType(type)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(setupResult.completionObserver);
        return ride;
    }


    @Test
    void itShould_listRides() {
        // Arrange
        Ride ride1 = buildRide(1L, RideType.TRIP, "Paris", "Outside Paris");
        Ride ride2 = buildRide(2L, RideType.JOURNEY, "Outside Paris", "Paris");
        setupResult.rideRepository.save(ride1);
        setupResult.rideRepository.save(ride2);

        // Act
        List<Ride> rides = setupResult.underTest.listRides(setupResult.customer);

        // Assert
        assertNotNull(rides);
        assertEquals(2, rides.size());

        for (Ride ride : rides) {
            assertNotNull(ride.getDriver());
        }

    }
}