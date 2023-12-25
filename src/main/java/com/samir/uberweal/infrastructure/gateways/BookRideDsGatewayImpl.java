package com.samir.uberweal.infrastructure.gateways;

import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.adapters.gateways.BookRideDsGateway;
import com.samir.uberweal.infrastructure.database.entities.RideDataMapper;
import com.samir.uberweal.infrastructure.database.entities.RiderDataMapper;
import com.samir.uberweal.infrastructure.database.repositories.JpaRideRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookRideDsGatewayImpl implements BookRideDsGateway {

    private final JpaRideRepository repository;
    @Override
    public void save(BookRide ride) {
        repository.save(rideToRideDataMapper(ride));
    }


    private RiderDataMapper riderToRiderDataMapper(Rider rider) {
        return RiderDataMapper.builder()
                .id(rider.getId())
                .funds(rider.getFunds())
                .name(rider.getName())
                .joinedAt(rider.getJoinedAt())
                .build();
    }

    private RideDataMapper rideToRideDataMapper(BookRide r) {
        return RideDataMapper.builder()
                .id(r.getId())
                .rider(riderToRiderDataMapper(r.getRider()))
                .rideType(r.getRideType().toString())
                .startLocation(r.getStartLocation().getName())
                .endLocation(r.getEndLocation().getName())
                .price(r.getPrice())
                .build();
    }

}

