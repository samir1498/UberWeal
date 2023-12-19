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
import com.samir.uberweal.core.domain.repositories.RideRepository;
import com.samir.uberweal.core.domain.repositories.stubs.CustomerRepositoryStub;
import com.samir.uberweal.core.domain.repositories.stubs.RideRepositoryStub;
import com.samir.uberweal.core.usecases.CustomerUseCaseImpl;
import com.samir.uberweal.core.usecases.ListPastRidesUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListPastRidesTest {
    private CustomerUseCase customerUseCase;

    private RideRepository rideRepository;

    private ListPastRidesUseCase underTest;

    private Customer customer;
    private Driver driver;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryStub();
        customerUseCase = setupCustomerUseCase(customerRepository);
        rideRepository = new RideRepositoryStub();
        underTest = setupListPastRidesUseCase(rideRepository);

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

    private ListPastRidesUseCase setupListPastRidesUseCase(RideRepository rideRepository) {
        return new ListPastRidesUseCaseImpl(rideRepository);
    }

    private CustomerUseCase setupCustomerUseCase(CustomerRepository customerRepository) {
        return new CustomerUseCaseImpl(customerRepository);
    }

    private Ride buildRide(
            Long id,
            Customer customer,
            Driver driver,
            RideType type,
            String startPoint,
            String destination
    ) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl(customerUseCase);

        Ride ride =  Ride.builder()
                .id(id)
                .customer(customer)
                .driver(driver)
                .destination(new Location(destination))
                .startingPoint(new Location(startPoint))
                .rideType(type)
                .status(RideStatus.IN_PROGRESS)
                .build();
        ride.addObserver(completionObserver);
        return ride;
    }

    @Test
    void itShould_listRides() {
        // Arrange
        Ride ride1 = buildRide(1L, customer, driver, RideType.TRIP, "Paris", "Outside Paris");
        Ride ride2 = buildRide(2L, customer, driver, RideType.JOURNEY, "Outside Paris", "Paris");
        rideRepository.save(ride1);
        rideRepository.save(ride2);

        // Act
        List<Ride> rides = underTest.listRides(customer);

        // Assert
        assertNotNull(rides);
        assertEquals(2, rides.size());

        for (Ride ride : rides) {
            assertNotNull(ride.getDriver());
        }

    }

}