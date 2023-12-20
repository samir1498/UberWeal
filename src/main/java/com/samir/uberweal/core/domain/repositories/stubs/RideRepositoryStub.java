package com.samir.uberweal.core.domain.repositories.stubs;

import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.repositories.RideRepository;

import java.util.*;

public class RideRepositoryStub implements RideRepository {

    private final Map<Long, Ride> ridesMap = new HashMap<>();

    @Override
    public Ride save(Ride ride) {
        ridesMap.put(ride.getId(), ride);
        return ride;
    }

    @Override
    public List<Ride> findAll(){
        return new ArrayList<>(ridesMap.values());
    }

    @Override
    public Optional<Ride> findById(Long rideId) {
        return Optional.of(ridesMap.get(rideId));
    }

    @Override
    public List<Ride> findByRiderId(Long riderId) {
        List<Ride> rides = new ArrayList<>();
        for (Ride ride : ridesMap.values()) {
            if (Objects.equals(ride.getRider().getId(), riderId)) {
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
