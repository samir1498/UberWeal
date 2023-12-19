package com.samir.uberweal.core.usecases;

import com.samir.uberweal.application.BookRideUseCase;
import com.samir.uberweal.application.CustomerUseCase;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.repositories.RideRepository;
import com.samir.uberweal.core.domain.services.pricing.calculator.RideChargeCalculator;
import com.samir.uberweal.core.domain.services.pricing.RideChargeCalculatorFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class BookRideUseCaseImpl implements BookRideUseCase {

    private final RideRepository rideRepository;
    private final CustomerUseCase customerUseCase;
    private final RideChargeCalculatorFactory rideChargeCalculatorFactory;

    @Override
    public Ride bookRide(Ride ride) {

        double price = calculateExpectedCharge(ride);

        customerUseCase.preAuthorize( ride.getCustomer(), price);

        if (price == 0) {
            ride.getCustomer().setVoucher(true);
        }

        rideRepository.save(ride);

        return ride;
    }

    public double calculateExpectedCharge(Ride ride) {
        RideChargeCalculator chargeCalculator = rideChargeCalculatorFactory.
                getRideChargeCalculator(ride.getRideType());

        if (chargeCalculator == null) {
            throw new IllegalArgumentException("Unsupported ride type");
        }

        double baseCharge = chargeCalculator.calculateCharge(ride);

        ride.setPrice(baseCharge);
        // Apply other discounts
        applyDiscount(ride);

        return ride.getPrice();
    }

    public void applyDiscount(Ride ride){
        applyFirstYearDiscount(ride);
        applyDistanceDiscount(ride);
    }
    private void applyDistanceDiscount(Ride ride) {
        double distance = ride.getDistance();
        double price = ride.getPrice();

        if (distance < 5 && distance > 0) {
            ride.setPrice(price - 5);
        }
    }

    private void applyFirstYearDiscount(Ride ride) {
        LocalDate joinedAt = ride.getCustomer().getJoinedAt();
        LocalDate today = LocalDate.now();
        double price = ride.getPrice();
        if (joinedAt.plusYears(1).isBefore(today)) {
            ride.setPrice(price / 2);
        }
    }

}
