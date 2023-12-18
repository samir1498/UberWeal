package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.driver.Driver;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.repositories.ride.RideRepository;
import com.samir.uberweal.core.usecases.customer.CustomerUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class RideUseCaseImpl implements RideUseCase {


    private final RideRepository rideRepository;
    private final CustomerUseCase customerUseCase;

    @Override
    public Ride bookRide(Ride ride) {

        double price = calculateExpectedCharge(ride);

        if (price == 0) {
            ride.getCustomer().setVoucher(true);
        }

        customerUseCase.preAuthorize( ride.getCustomer(), price);
        rideRepository.save(ride);

        return ride;
    }

    @Override
    public double calculateExpectedCharge(Ride ride) {
        boolean isRiderInParis = ride.getStartingPoint().getName().equals("Paris");
        boolean isDestinationParis = ride.getDestination().getName().equals("Paris");

        switch (ride.getRideType()) {
            case TRIP -> ride.setPrice(
                    (isRiderInParis && !isDestinationParis) ? 30 :
                            (!isRiderInParis && !isDestinationParis) ? 50 : -1
            );

            case JOURNEY -> ride.setPrice(
                    (!isRiderInParis && isDestinationParis) ? 0 :
                            (isRiderInParis && isDestinationParis) ? 10 : -1
            );
            default -> ride.setPrice(-1);
        }

        applyFirstYearDiscount(ride);

        applyDistanceDiscount(ride);

        return ride.getPrice();

    }

    private static void applyDistanceDiscount(Ride ride) {
        double distance = ride.getDistance();
        double price = ride.getPrice();

        if (distance < 5 && distance > 0) {
            ride.setPrice(price - 5);
        }
    }

    private static void applyFirstYearDiscount(Ride ride) {
        LocalDate joinedAt = ride.getCustomer().getJoinedAt();
        LocalDate today = LocalDate.now();
        double price = ride.getPrice();
        if (joinedAt.plusYears(1).isBefore(today)) {
            ride.setPrice(price / 2);
        }
    }

    private double calculateTripPrice(boolean isRiderInParis, boolean isDestinationParis){
        return (isRiderInParis && !isDestinationParis) ? 30 :
                (!isRiderInParis && !isDestinationParis) ? 50 : -1;
    }
    private double calculateJourneyPrice(boolean isRiderInParis, boolean isDestinationParis){
        return (isRiderInParis && !isDestinationParis) ? 30 :
                (!isRiderInParis && !isDestinationParis) ? 50 : -1;
    }


}