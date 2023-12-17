package com.samir.uberweal.useCases;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.customer.CustomerRepositoryStub;
import com.samir.uberweal.core.domain.entities.driver.Driver;
import com.samir.uberweal.core.domain.entities.ride.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import com.samir.uberweal.core.usecases.customer.CustomerUseCaseImpl;
import com.samir.uberweal.core.usecases.ride.RideUseCase;
import com.samir.uberweal.core.usecases.ride.RideUseCaseImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RideUseCaseTest {

    private RideUseCase underTest;

    private Customer customer;
    private Driver driver;

    @BeforeEach
    void setUp() {
        CustomerRepositoryStub riderRepositoryStub = new CustomerRepositoryStub();
        underTest = new RideUseCaseImpl(new CustomerUseCaseImpl(riderRepositoryStub));

        customer = Customer
                .builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        riderRepositoryStub.save(customer);
        driver = new Driver("driverId", "DriverName");
    }

    @AfterEach
    void tearDown() {
        customer.setFunds(100);
    }


    private Ride buildRide(RideType type, Location startPoint, Location destination, double distance) {
        return Ride.builder()
                .customer(customer)
                .driver(driver)
                .destination(destination)
                .startingPoint(startPoint)
                .rideType(type)
                .distance(distance)
                .build();
    }
    @ParameterizedTest(name = "{index}. {0} from {2} to {3} costs {1}€")
    @CsvSource({
            "TRIP, 30, Paris, Outside Paris",
            "JOURNEY, 0, Outside Paris, Paris",
            "JOURNEY, 10, Paris, Paris",
            "TRIP, 50, Outside Paris, Outside Paris",
    })
    @DisplayName("It Should Apply Correct Price")
    void itShould_bookDriver_ShouldApplyCorrectPrice(
            RideType type,
            double expectedPrice,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination, 0);

        // Act
        Ride bookedRide = underTest.bookRide(customer, driver, ride);

        // Assert
        assertEquals(expectedPrice, bookedRide.getPrice());
        assertEquals(type, bookedRide.getRideType());
    }


    @ParameterizedTest(name = "{index}. {0} from {1} to {2} should deduct {3}€")
    @CsvSource({
            "TRIP, Paris, Outside Paris, 30",
            "JOURNEY, Outside Paris, Paris, 0",
            "JOURNEY, Paris, Paris, 10",
            "TRIP, Outside Paris, Outside Paris, 50",
    })
    @DisplayName("Book a Ride When Customer has Sufficient Funds")
    void itShould_bookRide_WithSufficientFunds(
            RideType type,
            Location startPoint,
            Location destination,
            double cost
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination, 0);
        double initialFunds = customer.getFunds();

        // Act
        Ride bookedRide = underTest.bookRide(customer, driver, ride);

        // Assert
        assertNotNull(bookedRide);
        assertEquals(customer.getFunds(), initialFunds - cost);

    }

    @ParameterizedTest(name = "{index}. {0} from {1} to {2} should deduct half of {3}€ and set voucher to {4}")
    @CsvSource({
            "TRIP, Paris, Outside Paris, 30, false",
            "JOURNEY, Outside Paris, Paris, 0, true",
            "JOURNEY, Paris, Paris, 10, false",
            "TRIP, Outside Paris, Outside Paris, 50, false",
    })
    @DisplayName("Book a Ride With Half Price In The First Year")
    void itShould_bookRide_WithHalfPrice_InTheFirstYear(
            RideType type,
            Location startPoint,
            Location destination,
            double cost,
            boolean voucher
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination, 0);
        customer.setJoinedAt(LocalDate.now().minusYears(1).minusMonths(1));

        // Act
        Ride bookedRide = underTest.bookRide(customer, driver, ride);

        // Assert
        assertEquals(cost / 2, bookedRide.getPrice());

        assertEquals(bookedRide.getCustomer().isVoucher(), voucher);

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
    @DisplayName("Book a Ride With 5€ Discount When Distance is less Then 5km")
    void itShould_bookRide_With5eurosDiscount_WhenDistanceIsLessThenKm(
            RideType type,
            Location startPoint,
            Location destination,
            double cost,
            double distance
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination, distance);

        // Act
        Ride bookedRide = underTest.bookRide(customer, driver, ride);
        double price = bookedRide.getPrice();
        // Assert
        assertEquals(cost - 5, price);
        assertEquals(type, bookedRide.getRideType());
    }


}
