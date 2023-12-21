package com.samir.uberweal.domain.gateways.stubs;

import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.gateways.RiderDsGateway;

import java.util.HashMap;
import java.util.Map;

public class RiderDsGatewayStub implements RiderDsGateway {

    private final Map<Long, Rider> riderDatabase = new HashMap<>();
    private long currentId = 1;

    @Override
    public void save(Rider Rider) {
        // Assign an ID if the Rider is new
        if (Rider.getId() == null) {
            Rider.setId(currentId++);
        }

        // Save or update the Rider in the in-memory database
        riderDatabase.put(Rider.getId(), Rider);

    }

    @Override
    public Rider findRiderById(Long id) {
        // Retrieve a Rider from the in-memory database by ID
        return riderDatabase.get(id);
    }
}
