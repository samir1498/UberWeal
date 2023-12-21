package com.samir.uberweal.domain.gateways.stubs;

import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.gateways.ListPastRidesDsGateway;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class ListPastRidesDsGatewayStub implements ListPastRidesDsGateway {
    private final Map<Long, Ride> ridesMap = DBStub.ridesMap ;

    @Override
    public List<Ride> findByRiderId(Long riderId) {
        List<Ride> rides = new ArrayList<>();
        for (Ride ride : ridesMap.values()) {
            if (Objects.equals(ride.getRider().getId(), riderId)) {
                rides.add(ride);
            }
        }
        return rides;
    }
}
