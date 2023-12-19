package com.samir.uberweal.core.domain.repositories;

import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.util.List;

public interface RideRepository {
    Ride save(Ride ride);

    List<Ride> findByCustomerId(Long customerId);

    List<Ride> findAll();
    List<Ride> findByDriverId(String driverId);
}

