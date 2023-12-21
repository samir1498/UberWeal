package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.queries.GetAllRidesQuery;
import com.samir.uberweal.application.queries.GetRiderQuery;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.exceptions.RiderNotFoundException;
import com.samir.uberweal.domain.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.domain.gateways.RiderDsGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetRiderQueryHandler {

    private final RiderDsGateway gateway;


    public Rider handle(GetRiderQuery query) {
        return gateway.findRiderById(query.riderId());
    }
}
