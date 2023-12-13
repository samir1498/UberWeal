package com.samir.uberweal.core.useCases;

// RideUseCase.java

import com.samir.uberweal.core.domain.ride.Location;
import com.samir.uberweal.core.domain.ride.Ride;
import com.samir.uberweal.core.domain.ride.RideType;
import com.samir.uberweal.core.domain.driver.*;
import com.samir.uberweal.core.domain.rider.Rider;

public interface RideUseCase {
    Ride bookRide(Rider rider, Driver driver, Location destination, Location startingPoint, RideType rideType);
}

