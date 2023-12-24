package com.samir.uberweal.adapters.dtos;

import com.samir.uberweal.domain.entities.BookRide;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideDto {
    private Long id;
    private String riderName;
    private String startLocation;
    private String endLocation;
    private double price;
    private double distance;
    private String status;
    private String type;

    public static RideDto RideToRideDto(BookRide ride) {
        return RideDto.builder()
                .type(ride.getRideType().name())
                .riderName(ride.getRider().getName())
                .status(ride.getStatus().name())
                .startLocation(ride.getStartLocation().getName())
                .endLocation(ride.getEndLocation().getName())
                .build();
    }
}
