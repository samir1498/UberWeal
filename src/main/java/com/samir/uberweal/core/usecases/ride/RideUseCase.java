package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.util.List;

public interface RideUseCase {
    Ride bookRide(Ride ride);
    double calculateExpectedCharge(Ride ride);

    List<Ride> listRides(Customer customer);


}

