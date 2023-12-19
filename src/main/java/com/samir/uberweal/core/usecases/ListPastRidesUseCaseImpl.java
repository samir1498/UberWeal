package com.samir.uberweal.core.usecases;

import com.samir.uberweal.application.ListPastRidesUseCase;
import com.samir.uberweal.core.domain.entities.Customer;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.repositories.RideRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListPastRidesUseCaseImpl implements ListPastRidesUseCase {
    private final RideRepository rideRepository;
    @Override
    public List<Ride> listRides(Customer customer) {
        return rideRepository.findByCustomerId(customer.getId());
    }

}
