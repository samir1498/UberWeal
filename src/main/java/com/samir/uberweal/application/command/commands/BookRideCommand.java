package com.samir.uberweal.application.command.commands;

import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.Rider;
import lombok.Builder;

@Builder
public record BookRideCommand(
        Rider rider,
        Location startLocation,
        Location endLocation,
        double distance,
        BookRide.RideType type
) {
}
