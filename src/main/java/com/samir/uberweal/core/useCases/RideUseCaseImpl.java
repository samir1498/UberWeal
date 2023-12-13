package com.samir.uberweal.core.useCases;

import com.samir.uberweal.core.domain.ride.Location;
import com.samir.uberweal.core.domain.ride.Ride;
import com.samir.uberweal.core.domain.ride.RideType;
import com.samir.uberweal.core.domain.driver.*;
import com.samir.uberweal.core.domain.rider.Rider;

import java.util.UUID;

public class RideUseCaseImpl implements RideUseCase {

    @Override
    public Ride bookRide(Rider rider, Driver driver, Location destination, Location startingPoint, RideType rideType) {
        Ride ride = new Ride(UUID.randomUUID().toString(), rider, driver, destination, startingPoint, rideType);
        ride.calculatePrice();
        rider.getFunds();
        return ride;
    }
}