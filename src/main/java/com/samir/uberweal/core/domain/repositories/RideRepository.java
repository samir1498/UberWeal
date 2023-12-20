package com.samir.uberweal.core.domain.repositories;

import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.util.List;
import java.util.Optional;

public interface RideRepository {
    void save(Ride ride);

    List<Ride> findByRiderId(Long RiderId);

    List<Ride> findAll();

    Optional<Ride> findById(Long rideId);
}

