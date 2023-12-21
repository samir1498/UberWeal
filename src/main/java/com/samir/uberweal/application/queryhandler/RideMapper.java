package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.entities.ride.RideStatus;

public class RideMapper {

    public static RideDto mapToRideDto(Ride ride) {
        return RideDto.builder()
                .id(ride.getId())
                .riderName(ride.getRider().getName())
                .endLocation(ride.getEndLocation().getName())
                .startLocation(ride.getStartLocation().getName())
                .price(ride.getPrice())
                .distance(ride.getDistance())
                .status(RideStatus.COMPLETED)
                .build();
    }
}
