package com.samir.uberweal.adapters.gateways;

import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.exceptions.RiderNotFoundException;
import com.samir.uberweal.domain.gateways.RiderDsGateway;
import com.samir.uberweal.infrastructure.repositories.JpaRiderRepository;
import com.samir.uberweal.infrastructure.JpaEntities.RiderDataMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RiderDsGatewayImpl implements RiderDsGateway {

    private final JpaRiderRepository repository;
    @Override
    public void save(Rider rider) {
        repository.save(riderToRiderDataMapper(rider));
    }

    @Override
    public Rider findRiderById(Long id) {

        Optional<RiderDataMapper> rider = repository.findById(id);

        return riderDataMapperToRider(rider.orElseThrow(
                () -> new RiderNotFoundException("Rider not found with ID: " + id)
        ));
    }


    private Rider riderDataMapperToRider(RiderDataMapper rider) {
        return Rider.builder()
                .id(rider.getId())
                .funds(rider.getFunds())
                .name(rider.getName())
                .joinedAt(rider.getJoinedAt())
                .build();
    }
    private RiderDataMapper riderToRiderDataMapper(Rider rider) {
        return RiderDataMapper.builder()
                .id(rider.getId())
                .funds(rider.getFunds())
                .name(rider.getName())
                .joinedAt(rider.getJoinedAt())
                .build();
    }

}
