package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.query.queries.GetAllRidesQuery;
import com.samir.uberweal.application.query.handler.GetAllRidesQueryHandler;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.domain.observers.RideCompletionObserver;
import com.samir.uberweal.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.adapters.gateways.stubs.DBStub;
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
    private BookRide buildRide(
            Long id,
            Rider rider,
            BookRide.RideType type,
            String startPoint,
            String destination
    ) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl();

        BookRide ride =  BookRide.builder()
                .id(id)
                .rider(rider)
                .endLocation(new Location(destination))
                .startLocation(new Location(startPoint))
                .rideType(type)
                .status(BookRide.RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(completionObserver);
        return ride;
    }

    @Test
    void itShould_listRides() {
        // Arrange
        BookRide ride1 = buildRide(1L, rider, BookRide.RideType.TRIP, "Paris", "Outside Paris");
        BookRide ride2 = buildRide(2L, rider, BookRide.RideType.JOURNEY, "Outside Paris", "Paris");
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