package com.samir.uberweal.application;

import com.samir.uberweal.core.domain.entities.Customer;
import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.util.List;

public interface ListPastRidesUseCase {
    List<Ride> listRides(Customer customer);
}
