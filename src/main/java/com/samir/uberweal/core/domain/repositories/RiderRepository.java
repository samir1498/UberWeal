package com.samir.uberweal.core.domain.repositories;

import com.samir.uberweal.core.domain.entities.Rider;

import java.util.Optional;

public interface RiderRepository {

    void save(Rider Rider);
    Optional<Rider> findRiderById(Long Id);

}
