package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.driver.Driver;
import com.samir.uberweal.core.usecases.customer.CustomerUseCase;
import com.samir.uberweal.core.domain.entities.ride.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RideUseCaseImpl implements RideUseCase {

    private final CustomerUseCase customerUseCase;

    @Override
    public Ride bookRide(
            Customer customer,
            Driver driver,
            Ride ride
    ) {

        ride.calculatePrice();
        double price = ride.getPrice();
        double distance = ride.getDistance();

        if(distance < 5 && distance > 0){
            ride.setPrice(price - 5);
        }

        if(price == 0){
            customer.setVoucher(true);
        }

        if(customer.hasSufficientFunds(price)){
            Customer authorizedCustomer = customerUseCase.preAuthorize(customer, price);
            ride.setCustomer(customerUseCase.deductFunds(authorizedCustomer, price));
        }

        return ride;
    }

}