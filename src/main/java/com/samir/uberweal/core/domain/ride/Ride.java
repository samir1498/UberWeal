package com.samir.uberweal.core.domain.ride;

import com.samir.uberweal.core.domain.driver.Driver;
import com.samir.uberweal.core.domain.rider.Rider;
import lombok.Data;

@Data
public class Ride {
    private final String id;
    private final Rider rider;
    private final Driver driver;
    private final Location destination;
    private final Location startingPoint;
    private final RideType rideType;
    private double price = -1;

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

    }

}
