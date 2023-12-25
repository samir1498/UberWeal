package com.samir.uberweal.infrastructure.database.repositories;

import com.samir.uberweal.infrastructure.database.entities.RideDataMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRideRepository extends JpaRepository<RideDataMapper, Long> {
    List<RideDataMapper> findByRiderId(Long id);
}
