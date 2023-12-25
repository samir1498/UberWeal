package com.samir.uberweal.infrastructure.database.repositories;

import com.samir.uberweal.infrastructure.database.entities.RiderDataMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("JpaRiderRepository")
public interface JpaRiderRepository extends JpaRepository<RiderDataMapper, Long> {
}
