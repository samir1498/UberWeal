package com.samir.uberweal.adapters.gateways.stubs;

import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.adapters.gateways.ListPastRidesDsGateway;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class ListPastRidesDsGatewayStub implements ListPastRidesDsGateway {
    private final Map<Long, BookRide> ridesMap = DBStub.ridesMap ;

    @Override
    public List<BookRide> findByRiderId(Long riderId) {
        List<BookRide> rides = new ArrayList<>();
        for (BookRide ride : ridesMap.values()) {
            if (Objects.equals(ride.getRider().getId(), riderId)) {
                rides.add(ride);
            }
        }
        return rides;
    }
}
