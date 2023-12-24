package com.samir.uberweal.adapters.controllers;

import com.samir.uberweal.adapters.dtos.RideDto;
import com.samir.uberweal.application.command.handlers.BookRideCommandHandler;
import com.samir.uberweal.application.command.commands.BookRideCommand;
import com.samir.uberweal.application.query.queries.GetAllRidesQuery;
import com.samir.uberweal.application.query.queries.GetRiderQuery;
import com.samir.uberweal.application.query.handler.GetAllRidesQueryHandler;
import com.samir.uberweal.application.query.handler.GetRiderQueryHandler;
import com.samir.uberweal.domain.entities.BookRide;
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

    @GetMapping("/{id}")
    public List<RideDto> listPastRides(@PathVariable Long id){
        // mapping request to query 
        GetAllRidesQuery query = new GetAllRidesQuery(id);
        return handler.handle(query);
    }

    @PostMapping
    public RideDto bookRide(@RequestBody BookRide request){
        GetRiderQuery query = new GetRiderQuery(request.getRider().getId());
        BookRideCommand command = BookRideCommand.builder()
                .rider(riderQueryHandler.handle(query))
                //TODO distance calculation
                .distance(5)
                .startLocation(request.getStartLocation())
                .endLocation(request.getEndLocation())
                .type(request.getRideType())
                .build();

        return bookRideCommandHandler.handle(command);
    }

}
