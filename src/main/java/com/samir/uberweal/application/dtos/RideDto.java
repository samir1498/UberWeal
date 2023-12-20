package com.samir.uberweal.application.dtos;

import com.samir.uberweal.domain.entities.ride.RideStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideDto {
    private Long id;
    private String customerName;
    private String driverName;
    private String destination;
    private String startingPoint;
    private double price;
    private double distance;
    private RideStatus status;
}
