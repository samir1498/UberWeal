package com.samir.uberweal.domain.gateways.stubs;

import com.samir.uberweal.domain.gateways.BookRideDsGateway;
import com.samir.uberweal.domain.entities.ride.Ride;
import lombok.RequiredArgsConstructor;

import java.util.*;
@RequiredArgsConstructor
public class BookRideDsGatewayStub implements BookRideDsGateway {

    private final Map<Long, Ride> ridesMap = DBStub.ridesMap;

    @Override
    public void save(Ride ride) {
        ridesMap.put(ride.getId(), ride);
    }


}
