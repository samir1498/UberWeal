package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.adapters.dtos.ReMappers;
import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.queries.GetAllRidesQuery;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.gateways.ListPastRidesDsGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllRidesQueryHandler {

    private final ListPastRidesDsGateway listPastRidesDsGateway;

    public List<RideDto> handle(GetAllRidesQuery getAllRidesQuery) {
        List<Ride> rides = listPastRidesDsGateway.findByRiderId(getAllRidesQuery.riderId());
        return mapToRideDtoList(rides);
    }

    private List<RideDto> mapToRideDtoList(List<Ride> rides) {
        return rides.stream()
                .map(ReMappers::RideToRideDto)
                .collect(Collectors.toList());
    }
}
