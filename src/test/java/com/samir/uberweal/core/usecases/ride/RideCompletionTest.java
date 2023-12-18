package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.exceptions.InsufficientFundsException;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RideCompletionTest {

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


    private Ride buildRide(RideType type, Location startPoint, Location destination) {
        Ride ride =  Ride.builder()
                .customer(customer)
                .driver(driver)
                .destination(destination)
                .startingPoint(startPoint)
                .rideType(type)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(completionObserver);
        return ride;
    }

    @ParameterizedTest(name = "{index}. {0} from {1} to {2} Should Charge after Ride Completed")
    @CsvSource({
            "TRIP, Paris, Outside Paris",
            "JOURNEY, Outside Paris, Paris",
            "JOURNEY, Paris, Paris, false",
            "TRIP, Outside Paris, Outside Paris",
    })
    @DisplayName("It Should Charge Customer When Ride Is Complete")
    void itShould_ChargeCustomer_WhenRideIsComplete(
            RideType type,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        double initialFunds = customer.getFunds();
        Ride ride = buildRide(type, startPoint, destination);

        // Act
        Ride bookedRide = underTest.bookRide(ride);
        bookedRide.completeRide();

        // Assert
        double finalFunds = customer.getFunds();
        double expectedCharge = underTest.calculateExpectedCharge(bookedRide);

        assertEquals(initialFunds - expectedCharge, finalFunds);
        assertEquals(RideStatus.COMPLETED, bookedRide.getStatus());
    }


    @ParameterizedTest(name = "{index}. {0} from {1} to {2}")
    @CsvSource({
            "TRIP, Paris, Outside Paris",
            "JOURNEY, Paris, Paris",
            "TRIP, Outside Paris, Outside Paris",
    })
    @DisplayName("Should not Book a Ride When Customer doesn't have Sufficient Funds")
    void itShouldNot_bookRide_WithNoSufficientFunds(
            RideType type,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        Ride ride = buildRide(type, startPoint, destination);
        customer.setFunds(0);
        double initialFunds = customer.getFunds();

        // Act
        assertThatThrownBy(() -> underTest.bookRide(ride))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds to deduct: " + ride.getPrice());

        // Assert
        assertEquals(customer.getFunds(), initialFunds);
    }


}
