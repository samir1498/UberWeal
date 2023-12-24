package com.samir.uberweal.application.query.handler;

import com.samir.uberweal.application.query.queries.GetRiderQuery;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.adapters.gateways.RiderDsGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetRiderQueryHandler {

    private final RiderDsGateway gateway;


    public Rider handle(GetRiderQuery query) {
        return gateway.findRiderById(query.riderId());
    }
}
