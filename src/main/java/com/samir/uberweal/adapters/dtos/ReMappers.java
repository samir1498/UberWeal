package com.samir.uberweal.adapters.dtos;

import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.infrastructure.jpa.entities.RideDataMapper;
import com.samir.uberweal.infrastructure.jpa.entities.RiderDataMapper;

public class ReMappers {
    public static Rider riderDataMapperToRider(RiderDataMapper rider) {
        return Rider.builder()
                .id(rider.getId())
                .funds(rider.getFunds())
                .name(rider.getName())
                .joinedAt(rider.getJoinedAt())
                .build();
    }

    public static RiderDataMapper riderToRiderDataMapper(Rider rider) {
        return RiderDataMapper.builder()
                .id(rider.getId())
                .funds(rider.getFunds())
                .name(rider.getName())
                .joinedAt(rider.getJoinedAt())
                .build();
    }

    public static RideDataMapper rideToRideDataMapper(Ride r) {
        return RideDataMapper.builder()
                .id(r.getId())
                .rider(riderToRiderDataMapper(r.getRider()))
                .rideType(r.getRideType().toString())
                .startLocation(r.getStartLocation().getName())
                .endLocation(r.getEndLocation().getName())
                .price(r.getPrice())
                .build();
    }

    public static RideDto RideToRideDto(Ride ride) {
        return RideDto.builder()
                .type(ride.getRideType())
                .riderName(ride.getRider().getName())
                .status(ride.getStatus())
                .startLocation(ride.getStartLocation().getName())
                .endLocation(ride.getEndLocation().getName())
                .build();
    }
}
