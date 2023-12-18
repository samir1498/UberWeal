package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.core.domain.repositories.customer.CustomerRepositoryStub;
import com.samir.uberweal.core.domain.entities.driver.Driver;
import com.samir.uberweal.core.domain.entities.location.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideStatus;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import com.samir.uberweal.core.domain.repositories.ride.RideRepository;
import com.samir.uberweal.core.domain.repositories.ride.RideRepositoryStub;
import com.samir.uberweal.core.usecases.customer.CustomerUseCaseImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RideDiscountTest {

    private RideUseCase underTest;
    private RideCompletionObserverImpl completionObserver;

    private Customer customer;
    private Driver driver;

    @BeforeEach
    void setUp() {
        CustomerRepositoryStub customerRepositoryStub = new CustomerRepositoryStub();
        CustomerUseCaseImpl customerUseCase = new CustomerUseCaseImpl(customerRepositoryStub);
        RideRepository rideRepository = new RideRepositoryStub();
        underTest = new RideUseCaseImpl(rideRepository, customerUseCase);
        completionObserver = new RideCompletionObserverImpl(underTest, customerUseCase);

        customer = Customer
                .builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        customerRepositoryStub.save(customer);
        driver = new Driver("driverId", "DriverName");
    }

    @AfterEach
    void tearDown() {
        customer.setFunds(100);
    }


    private Ride buildRide(RideType type, Location startPoint, Location destination, double distance) {
        Ride ride =  Ride.builder()
                .customer(customer)
                .driver(driver)
                .destination(destination)
                .startingPoint(startPoint)
                .rideType(type)
                .distance(distance)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(completionObserver);
        return ride;
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
        Ride ride = buildRide(type, startPoint, destination, 0);
        // simulating that a customer joined less then a year ago
        customer.setJoinedAt(LocalDate.now().minusYears(1).minusMonths(1));

        // Act
        Ride bookedRide = underTest.bookRide(ride);

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
    @DisplayName("Apply 5€ Discount When Distance is less Then 5km")
    void itShould_Apply5eurosDiscount_WhenDistanceIsLessThen5Km(
            RideType type,
            Location startPoint,
            Location destination,
            double cost,
            double distance
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination, distance);

        // Act
        Ride bookedRide = underTest.bookRide(ride);
        double price = bookedRide.getPrice();
        // Assert
        assertEquals(cost - 5, price);
        assertEquals(type, bookedRide.getRideType());
    }


}
