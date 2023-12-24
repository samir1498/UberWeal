package com.samir.uberweal.adapters.gateways;

import com.samir.uberweal.domain.entities.BookRide;

import java.util.List;

public interface ListPastRidesDsGateway {
    List<BookRide> findByRiderId(Long RiderId);

}

