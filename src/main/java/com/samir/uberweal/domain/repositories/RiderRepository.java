package com.samir.uberweal.domain.repositories;

import com.samir.uberweal.domain.entities.Rider;

import java.util.Optional;

public interface RiderRepository {

    void save(Rider Rider);
    Optional<Rider> findRiderById(Long Id);

}
