package com.samir.uberweal.core.usecases.ride;

// RideUseCase.java

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.driver.Driver;
import com.samir.uberweal.core.domain.entities.ride.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideType;

public interface RideUseCase {
    Ride bookRide(Customer customer, Driver driver, Ride ride);
}

