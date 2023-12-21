package com.samir.uberweal.domain.gateways;

import com.samir.uberweal.domain.entities.Rider;

import java.util.Optional;

public interface RiderDsGateway {

    void save(Rider Rider);
    Rider findRiderById(Long Id);

}
