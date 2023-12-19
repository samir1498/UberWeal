package com.samir.uberweal.application;

import com.samir.uberweal.core.domain.entities.Customer;
import com.samir.uberweal.core.domain.entities.Driver;
import com.samir.uberweal.core.domain.entities.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideStatus;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import com.samir.uberweal.core.domain.observers.RideCompletionObserver;
import com.samir.uberweal.core.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.core.domain.repositories.CustomerRepository;
import com.samir.uberweal.core.domain.repositories.stubs.CustomerRepositoryStub;
import com.samir.uberweal.core.usecases.CustomerUseCaseImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RideCompletionTest {
    private CustomerUseCase customerUseCase;
    private Customer customer;
    private Driver driver;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryStub();
        customerUseCase = new CustomerUseCaseImpl(customerRepository);

        customer = Customer.builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        customerRepository.save(customer);

        driver = Driver.builder()
                .name("driver")
                .id(1L)
                .build();
    }

    private Ride buildRide(
            Customer customer,
            Driver driver,
            RideType type,
            Location startPoint,
            Location destination,
            double expectedCharge
    ) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl(customerUseCase);

        Ride ride =  Ride.builder()
                .customer(customer)
                .driver(driver)
                .destination(destination)
                .startingPoint(startPoint)
                .rideType(type)
                .price(expectedCharge)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(completionObserver);
        return ride;
    }
    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest(name = "{index}. {0} from {1} to {2} Should Charge {3} after Ride Completed")
    @CsvSource({
            "TRIP, Paris, Outside Paris, 30",
            "JOURNEY, Outside Paris, Paris, 0",
            "JOURNEY, Paris, Paris, 10",
            "TRIP, Outside Paris, Outside Paris, 50",
    })
    @DisplayName("It Should Charge Customer When Ride Is Complete")
    void itShould_ChargeCustomer_WhenRideIsComplete(
            RideType type,
            Location startPoint,
            Location destination,
            double expectedCharge
    ) {
        // Arrange
        double initialFunds = customer.getFunds();
        Ride ride = buildRide(customer, driver, type, startPoint, destination, expectedCharge);

        // Act
        ride.completeRide();

        // Assert
        double finalFunds = customer.getFunds();

        assertEquals(initialFunds - expectedCharge, finalFunds);
        assertEquals(RideStatus.COMPLETED, ride.getStatus());
    }


}