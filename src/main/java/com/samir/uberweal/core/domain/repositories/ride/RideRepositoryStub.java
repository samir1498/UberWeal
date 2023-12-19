package com.samir.uberweal.core.domain.repositories.ride;

import com.samir.uberweal.core.domain.entities.ride.Ride;

import java.util.*;

public class RideRepositoryStub implements RideRepository {

    private final Map<Long, Ride> ridesMap = new HashMap<>();

    @Override
    public Ride save(Ride ride) {
        ridesMap.put(ride.getId(), ride);
        return ride;
    }

    @Override
    public List<Ride> findByCustomerId(Long riderId) {
        List<Ride> rides = new ArrayList<>();
        for (Ride ride : ridesMap.values()) {
            if (Objects.equals(ride.getCustomer().getId(), riderId)) {
                rides.add(ride);
            }
        }
        return rides;
    }

    @Override
    public List<Ride> findByDriverId(String driverId) {
        return null;
    }

}
