package com.samir.uberweal.infrastructure.repositories;

import com.samir.uberweal.infrastructure.JpaEntities.RideDataMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRideRepository extends JpaRepository<RideDataMapper, Long> {
    List<RideDataMapper> findByRiderId(Long id);
}
