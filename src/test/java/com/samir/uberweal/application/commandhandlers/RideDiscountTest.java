package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.application.commands.BookRideCommand;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.entities.Location;
import com.samir.uberweal.domain.entities.ride.Ride;
import com.samir.uberweal.domain.entities.ride.RideType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static com.samir.uberweal.RideTestSetup.*;
import static org.junit.jupiter.api.Assertions.*;

class RideDiscountTest {
    private Rider rider;
    private BookRideCommandHandler underTest;

    @BeforeEach
    void setUp() {
        rider = setupRider();
        underTest = setupBookRideCommandHandler();
    }

    @ParameterizedTest(name = "{index}. {0} from {1} to {2} should deduct half of {3}€ and set voucher to {4}")
    @CsvSource({
            "TRIP, Paris, Outside Paris, 30, false",
            "JOURNEY, Outside Paris, Paris, 0, true",
            "JOURNEY, Paris, Paris, 10, false",
            "TRIP, Outside Paris, Outside Paris, 50, false",
    })
    @DisplayName("Apply Half Price Discount In The First Year")
    void itShould_applyDiscount_WithHalfPrice_InTheFirstYear(
            RideType type,
            Location startPoint,
            Location destination,
            double cost,
            boolean voucher
    ) {
        // Arrange
        BookRideCommand ride = buildBookRideCommand(rider, type, startPoint, destination, 0);
        // simulating that a Rider joined less then a year ago
        rider.setJoinedAt(LocalDate.now().minusYears(1).minusMonths(1));

        // Act
        underTest.handle(ride);
        Ride bookedRide = LIST_PAST_RIDES_DS_GATEWAY.findByRiderId(rider.getId()).get(0);

        // Assert
        assertEquals(cost / 2, bookedRide.getPrice());

        assertEquals(bookedRide.getRider().isVoucher(), voucher);

        assertEquals(type, bookedRide.getRideType());
    }


    //If the number of kilometers is less than 5 km, then it will have a 5-euro discount.
    @ParameterizedTest(name = "{index}. {0} from {1} to {2} should discount 5€ from {3}€ ")
    @CsvSource({
            "TRIP, Paris, Outside Paris, 30, 4",
            "JOURNEY, Outside Paris, Paris, 0, 4.5",
            "JOURNEY, Paris, Paris, 10, 3",
            "TRIP, Outside Paris, Outside Paris, 50, 2",
    })
    @DisplayName("Apply 5€ Discount When Distance is less Then 5km")
    void itShould_Apply5eurosDiscount_WhenDistanceIsLessThen5Km(
            RideType type,
            Location startPoint,
            Location destination,
            double cost,
            double distance
    ) {
        // Arrange
        BookRideCommand ride = buildBookRideCommand(rider, type, startPoint, destination, distance);

        // Act
        underTest.handle(ride);
        Ride bookedRide = LIST_PAST_RIDES_DS_GATEWAY.findByRiderId(rider.getId()).get(0);
        double price = bookedRide.getPrice();
        // Assert
        assertEquals(cost - 5, price);
        assertEquals(type, bookedRide.getRideType());
    }


}