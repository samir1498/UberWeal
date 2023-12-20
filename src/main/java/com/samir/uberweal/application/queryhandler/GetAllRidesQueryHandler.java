package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.application.dtos.RideDto;
import com.samir.uberweal.application.queries.GetAllRidesQuery;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.repositories.RideRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllRidesQueryHandler {

    private final RideRepository rideRepository;


    public List<RideDto> handle(GetAllRidesQuery getAllRidesQuery) {
        List<Ride> rides = rideRepository.findByRiderId(getAllRidesQuery.riderId());
        return mapToRideDtoList(rides);
    }

    private List<RideDto> mapToRideDtoList(List<Ride> rides) {
        return rides.stream()
                .map(RideMapper::mapToRideDto)
                .collect(Collectors.toList());
    }
}
