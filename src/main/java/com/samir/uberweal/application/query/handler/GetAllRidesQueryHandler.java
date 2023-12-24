package com.samir.uberweal.application.query.handler;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.query.queries.GetAllRidesQuery;
import com.samir.uberweal.domain.entities.BookRide;
import com.samir.uberweal.adapters.gateways.ListPastRidesDsGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetAllRidesQueryHandler {

    private final ListPastRidesDsGateway listPastRidesDsGateway;

    public List<RideDto> handle(GetAllRidesQuery getAllRidesQuery) {
        List<BookRide> rides = listPastRidesDsGateway.findByRiderId(getAllRidesQuery.riderId());
        return mapToRideDtoList(rides);
    }

    private List<RideDto> mapToRideDtoList(List<BookRide> rides) {
        return rides.stream()
                .map(RideDto::RideToRideDto)
                .collect(Collectors.toList());
    }
}
