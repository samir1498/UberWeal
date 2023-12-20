package com.samir.uberweal.application.commands;

import com.samir.uberweal.core.domain.entities.Location;
import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import lombok.Builder;

@Builder
public record BookRideCommand(
        Rider rider,
        Location riderLocation,
        Location destination,
        double distance,
        RideType type
) {
}
