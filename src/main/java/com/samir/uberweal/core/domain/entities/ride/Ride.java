package com.samir.uberweal.core.domain.entities.ride;

import com.samir.uberweal.core.domain.entities.driver.Driver;

import java.time.LocalDate;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ride {
    private Long id;
    private Customer customer;
    private Driver driver;
    private final Location destination;
    private final Location startingPoint;
    private final RideType rideType;
    @Builder.Default
    private double price = -1;
    private double distance;

    public void calculatePrice() {
        boolean isRiderInParis = this.getStartingPoint().getName().equals("Paris");
        boolean isDestinationParis = this.getDestination().getName().equals("Paris");

        switch (this.getRideType()) {
            case TRIP -> this.setPrice(
                    (isRiderInParis && !isDestinationParis) ? 30 :
                            (!isRiderInParis && !isDestinationParis) ? 50 : -1
            );

            case JOURNEY -> this.setPrice(
                    (!isRiderInParis && isDestinationParis) ? 0 :
                            (isRiderInParis && isDestinationParis) ? 10 : -1
            );
            default -> this.setPrice(-1);
        }

        LocalDate joinedAt = customer.getJoinedAt();
        LocalDate today = LocalDate.now();
        if(joinedAt.plusYears(1).isBefore(today)) {
        	this.setPrice(getPrice()/2);
        }

    }

}
