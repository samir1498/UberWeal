package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.application.dtos.RideDto;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.entities.ride.RideStatus;

public class RideMapper {

    public static RideDto mapToRideDto(Ride ride) {
        return RideDto.builder()
                .id(ride.getId())
                .customerName(ride.getRider().getName())
                .destination(ride.getDestination().getName())
                .startingPoint(ride.getStartingPoint().getName())
                .price(ride.getPrice())
                .distance(ride.getDistance())
                .status(RideStatus.COMPLETED)
                .build();
    }
}
