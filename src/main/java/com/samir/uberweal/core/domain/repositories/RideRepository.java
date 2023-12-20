package com.samir.uberweal.core.domain.repositories;

import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.util.List;
import java.util.Optional;

public interface RideRepository {
    Ride save(Ride ride);

    List<Ride> findByRiderId(Long RiderId);

    List<Ride> findAll();
    List<Ride> findByDriverId(String driverId);

    Optional<Ride> findById(Long rideId);
}

