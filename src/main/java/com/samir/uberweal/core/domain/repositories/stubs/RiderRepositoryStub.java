package com.samir.uberweal.core.domain.repositories.stubs;

import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.repositories.RiderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RiderRepositoryStub implements RiderRepository {

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
    public Optional<Rider> findRiderById(Long id) {
        // Retrieve a Rider from the in-memory database by ID
        return Optional.ofNullable(riderDatabase.get(id));
    }
}
