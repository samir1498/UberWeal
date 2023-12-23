package com.samir.uberweal.adapters.gateways;

import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.infrastructure.jpa.repositories.JpaRideRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.samir.uberweal.adapters.dtos.ReMappers.riderDataMapperToRider;

@RequiredArgsConstructor
public class ListPastRidesDsGatewayImpl implements ListPastRidesDsGateway {

    private final JpaRideRepository repository;


    @Override
    public List<Ride> findByRiderId(Long riderId) {
        return repository.findByRiderId(riderId).stream()
                .map(r ->  Ride.builder()
                            .id(r.getId())
                            .rider(riderDataMapperToRider(r.getRider()))
                            .startLocation(new Location(r.getStartLocation()))
                            .endLocation(new Location(r.getEndLocation()))
                            .price(r.getPrice())
                            .build()
                ).toList();
    }


}
