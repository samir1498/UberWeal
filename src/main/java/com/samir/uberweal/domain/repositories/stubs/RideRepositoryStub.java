package com.samir.uberweal.domain.repositories.stubs;

import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.repositories.RideRepository;

import java.util.*;

public class RideRepositoryStub implements RideRepository {

    private final Map<Long, Ride> ridesMap = new HashMap<>();

    @Override
    public void save(Ride ride) {
        ridesMap.put(ride.getId(), ride);
    }

    @Override
    public List<Ride> findAll(){
        return new ArrayList<>(ridesMap.values());
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

}
