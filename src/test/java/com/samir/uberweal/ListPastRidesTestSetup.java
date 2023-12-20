package com.samir.uberweal;

import com.samir.uberweal.application.queryhandler.GetAllRidesQueryHandler;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.repositories.RideRepository;
import com.samir.uberweal.domain.repositories.stubs.RideRepositoryStub;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ListPastRidesTestSetup {

    public final static RideRepository rideRepository = new RideRepositoryStub();

    public static Rider setupRider() {
        return Rider.builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
    }



    public static GetAllRidesQueryHandler setupRideQueryHandler() {
        return new GetAllRidesQueryHandler(rideRepository);
    }


}
