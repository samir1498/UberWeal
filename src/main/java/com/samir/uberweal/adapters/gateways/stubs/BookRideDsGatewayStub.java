package com.samir.uberweal.adapters.gateways.stubs;

import com.samir.uberweal.adapters.gateways.BookRideDsGateway;
import com.samir.uberweal.domain.entities.BookRide;
import lombok.RequiredArgsConstructor;

import java.util.*;
@RequiredArgsConstructor
public class BookRideDsGatewayStub implements BookRideDsGateway {

    private final Map<Long, BookRide> ridesMap = DBStub.ridesMap;

    @Override
    public void save(BookRide ride) {
        ridesMap.put(ride.getId(), ride);
    }


}
