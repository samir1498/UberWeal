package com.samir.uberweal.adapters.gateways;

import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.gateways.BookRideDsGateway;
import com.samir.uberweal.infrastructure.repositories.JpaRideRepository;
import lombok.RequiredArgsConstructor;

import static com.samir.uberweal.adapters.dtos.ReMappers.rideToRideDataMapper;

@RequiredArgsConstructor
public class BookRideDsGatewayImpl implements BookRideDsGateway {

    private final JpaRideRepository repository;
    @Override
    public void save(Ride ride) {
        repository.save(rideToRideDataMapper(ride));
    }




}

