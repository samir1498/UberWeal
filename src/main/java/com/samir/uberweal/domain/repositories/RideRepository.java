package com.samir.uberweal.domain.repositories;

import com.samir.uberweal.domain.entities.ride.Ride;

import java.util.List;

public interface RideRepository {
    void save(Ride ride);

    List<Ride> findByRiderId(Long RiderId);

    List<Ride> findAll();

}

