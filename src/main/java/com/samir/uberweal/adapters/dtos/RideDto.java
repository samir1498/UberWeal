package com.samir.uberweal.adapters.dtos;

import com.samir.uberweal.domain.entities.ride.RideStatus;
import com.samir.uberweal.domain.entities.ride.RideType;
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
    private RideStatus status;
    private RideType type;
}
