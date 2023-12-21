package com.samir.uberweal.domain.gateways;

import com.samir.uberweal.domain.entities.ride.Ride;

import java.util.List;

public interface ListPastRidesDsGateway {
    List<Ride> findByRiderId(Long RiderId);

}

