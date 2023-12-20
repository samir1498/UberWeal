package com.samir.uberweal.core.domain.entities.ride;

import com.samir.uberweal.core.domain.entities.Driver;

import java.util.ArrayList;
import java.util.List;

import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.entities.Location;
import com.samir.uberweal.core.domain.observers.RideCompletionObserver;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ride {
    private Long id;
    private final Rider rider;
    private final Driver driver;
    private final Location destination;
    private final Location startingPoint;
    private final RideType rideType;
    @Builder.Default
    private double price = -1;
    private final double distance;
    private RideStatus status;
    @Builder.Default
    private List<RideCompletionObserver> completionObservers = new ArrayList<>();

    public void addObserver(RideCompletionObserver observer) {
        completionObservers.add(observer);
    }

    private void notifyObservers() {
        for (RideCompletionObserver observer : completionObservers) {
            observer.rideCompleted(this);
        }
    }

    // Public method to trigger completion and notify observers
    public void completeRide() {
        // Assuming there's a method to complete the ride
        setStatus(RideStatus.COMPLETED);

        // Notify observers
        notifyObservers();
    }

}
