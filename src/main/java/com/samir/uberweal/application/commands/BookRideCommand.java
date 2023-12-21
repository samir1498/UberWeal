package com.samir.uberweal.application.commands;

import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.ride.RideType;
import lombok.Builder;

@Builder
public record BookRideCommand(
        Rider rider,
        Location startLocation,
        Location endLocation,
        double distance,
        RideType type
) {
}
