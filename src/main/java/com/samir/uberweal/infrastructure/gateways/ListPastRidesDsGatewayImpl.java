package com.samir.uberweal.infrastructure.gateways;

import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.adapters.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.infrastructure.database.entities.RiderDataMapper;
import com.samir.uberweal.infrastructure.database.repositories.JpaRideRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListPastRidesDsGatewayImpl implements ListPastRidesDsGateway {

    private final JpaRideRepository repository;


    @Override
    public List<BookRide> findByRiderId(Long riderId) {
        return repository.findByRiderId(riderId).stream()
                .map(r ->  BookRide.builder()
                            .id(r.getId())
                            .rider(riderDataMapperToRider(r.getRider()))
                            .startLocation(new Location(r.getStartLocation()))
                            .endLocation(new Location(r.getEndLocation()))
                            .price(r.getPrice())
                            .build()
                ).toList();
    }

    private Rider riderDataMapperToRider(RiderDataMapper rider) {
        return Rider.builder()
                .id(rider.getId())
                .funds(rider.getFunds())
                .name(rider.getName())
                .joinedAt(rider.getJoinedAt())
                .build();
    }



}
