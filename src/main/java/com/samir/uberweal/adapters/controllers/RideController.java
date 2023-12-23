package com.samir.uberweal.adapters.controllers;

import com.samir.uberweal.adapters.presenters.BookRideRequest;
import com.samir.uberweal.adapters.presenters.ListPastRidesRequest;
import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.command.handlers.BookRideCommandHandler;
import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.application.query.queries.GetAllRidesQuery;
import com.samir.uberweal.application.query.queries.GetRiderQuery;
import com.samir.uberweal.application.query.handler.GetAllRidesQueryHandler;
import com.samir.uberweal.application.query.handler.GetRiderQueryHandler;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.ride.RideType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ride")
@RequiredArgsConstructor
public class RideController {

    private final GetAllRidesQueryHandler handler;
    private final GetRiderQueryHandler riderQueryHandler;
    private final BookRideCommandHandler bookRideCommandHandler;

    @GetMapping
    public List<RideDto> listPastRides(@RequestBody ListPastRidesRequest request){
        // mapping request to query 
        GetAllRidesQuery query = new GetAllRidesQuery(request.riderId());
        return handler.handle(query);
    }

    @PostMapping
    public RideDto bookRide(@RequestBody BookRideRequest request){
        GetRiderQuery query = new GetRiderQuery(request.riderId());
        BookRideCommand command = BookRideCommand.builder()
                .rider(riderQueryHandler.handle(query))
                //TODO distance calculation
                .distance(5)
                .startLocation(new Location(request.startLocation()))
                .endLocation(new Location(request.endLocation()))
                .type(RideType.valueOf(request.type()))
                .build();

        return bookRideCommandHandler.handle(command);
    }

}
