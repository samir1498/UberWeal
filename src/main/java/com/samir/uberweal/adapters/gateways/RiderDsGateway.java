package com.samir.uberweal.adapters.gateways;

import com.samir.uberweal.domain.entities.Rider;

public interface RiderDsGateway {

    void save(Rider Rider);
    Rider findRiderById(Long Id);

}
