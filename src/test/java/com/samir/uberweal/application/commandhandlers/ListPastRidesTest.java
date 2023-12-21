package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.queries.GetAllRidesQuery;
import com.samir.uberweal.application.queryhandler.GetAllRidesQueryHandler;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.entities.ride.RideStatus;
import com.samir.uberweal.domain.entities.ride.RideType;
import com.samir.uberweal.domain.observers.RideCompletionObserver;
import com.samir.uberweal.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.domain.gateways.stubs.DBStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.samir.uberweal.RideTestSetup.*;
import static org.junit.jupiter.api.Assertions.*;

class ListPastRidesTest {
    private Rider rider;
    private GetAllRidesQueryHandler underTest;

    @BeforeEach
    void setUp() {
        rider = setupRider();
        underTest = setupRideQueryHandler();
        DBStub.ridesMap.clear();
    }
    private Ride buildRide(
            Long id,
            Rider rider,
            RideType type,
            String startPoint,
            String destination
    ) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl();

        Ride ride =  Ride.builder()
                .id(id)
                .rider(rider)
                .endLocation(new Location(destination))
                .startLocation(new Location(startPoint))
                .rideType(type)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(completionObserver);
        return ride;
    }

    @Test
    void itShould_listRides() {
        // Arrange
        Ride ride1 = buildRide(1L, rider, RideType.TRIP, "Paris", "Outside Paris");
        Ride ride2 = buildRide(2L, rider, RideType.JOURNEY, "Outside Paris", "Paris");
        BOOK_RIDE_DS_GATEWAY.save(ride1);
        BOOK_RIDE_DS_GATEWAY.save(ride2);

        GetAllRidesQuery query = new GetAllRidesQuery(rider.getId());
        // Act
        List<RideDto> rides = underTest.handle(query);

        // Assert
        assertNotNull(rides);
        assertEquals(2, rides.size());

    }

}