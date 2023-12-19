package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.repositories.ride.RideRepository;
import com.samir.uberweal.core.domain.services.DiscountService;
import com.samir.uberweal.core.usecases.customer.CustomerUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class RideUseCaseImpl implements RideUseCase {


    private final RideRepository rideRepository;
    private final CustomerUseCase customerUseCase;
    private final DiscountService discountService;
    private final RideChargeCalculatorFactory rideChargeCalculatorFactory;

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
        RideChargeCalculator chargeCalculator = rideChargeCalculatorFactory.getRideChargeCalculator(ride.getRideType());

        if (chargeCalculator == null) {
            throw new IllegalArgumentException("Unsupported ride type");
        }

        double baseCharge = chargeCalculator.calculateCharge(ride);

        ride.setPrice(baseCharge);
        // Apply other discounts
        discountService.applyFirstYearDiscount(ride);
        discountService.applyDistanceDiscount(ride);

        return ride.getPrice();

    }

    @Override
    public List<Ride> listRides(Customer customer) {
        return rideRepository.findByCustomerId(customer.getId());
    }


}