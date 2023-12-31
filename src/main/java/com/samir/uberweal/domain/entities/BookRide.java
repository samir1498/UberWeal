package com.samir.uberweal.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.samir.uberweal.domain.observers.RideCompletionObserver;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRide {

    public enum RideStatus {
        COMPLETED,
        IN_PROGRESS
    }
    public enum RideType {
        JOURNEY,
        TRIP
    }

    private Long id;
    private final Rider rider;
    private final Location endLocation;
    private final Location startLocation;
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
