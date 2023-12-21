package com.samir.uberweal.application.queryhandler;

import com.samir.uberweal.application.queries.GetRiderQuery;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.gateways.RiderDsGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetRiderQueryHandler {

    private final RiderDsGateway gateway;


    public Rider handle(GetRiderQuery query) {
        return gateway.findRiderById(query.riderId());
    }
}
