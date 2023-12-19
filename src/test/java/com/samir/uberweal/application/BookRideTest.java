package com.samir.uberweal.application;

import com.samir.uberweal.core.domain.entities.Customer;
import com.samir.uberweal.core.domain.entities.Driver;
import com.samir.uberweal.core.domain.entities.Location;
import com.samir.uberweal.core.domain.entities.ride.Ride;
import com.samir.uberweal.core.domain.entities.ride.RideStatus;
import com.samir.uberweal.core.domain.entities.ride.RideType;
import com.samir.uberweal.core.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.core.domain.observers.RideCompletionObserver;
import com.samir.uberweal.core.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.core.domain.repositories.CustomerRepository;
import com.samir.uberweal.core.domain.repositories.RideRepository;
import com.samir.uberweal.core.domain.repositories.stubs.CustomerRepositoryStub;
import com.samir.uberweal.core.domain.repositories.stubs.RideRepositoryStub;
import com.samir.uberweal.core.domain.services.pricing.strategies.JourneyRideChargeCalculator;
import com.samir.uberweal.core.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.core.domain.services.pricing.strategies.TripRideChargeCalculator;
import com.samir.uberweal.core.usecases.BookRideUseCaseImpl;
import com.samir.uberweal.core.usecases.CustomerUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class BookRideTest {

    private  RideRepository rideRepository;
    private CustomerUseCase customerUseCase;

    private BookRideUseCase underTest;

    private Customer customer;
    private Driver driver;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryStub();
        customerUseCase = setupCustomerUseCase(customerRepository);
        underTest = setUpBookRideUseCase();

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

    private BookRideUseCase setUpBookRideUseCase() {
        rideRepository = new RideRepositoryStub();
        TripRideChargeCalculator tripRideChargeCalculator = new TripRideChargeCalculator();
        JourneyRideChargeCalculator journeyRideChargeCalculator = new JourneyRideChargeCalculator();
        RideChargeCalculatorFactory rideChargeCalculatorFactor = new RideChargeCalculatorFactory(tripRideChargeCalculator, journeyRideChargeCalculator);

        return new BookRideUseCaseImpl(rideRepository, customerUseCase,rideChargeCalculatorFactor);
    }

    private CustomerUseCase setupCustomerUseCase(CustomerRepository customerRepository) {
        return new CustomerUseCaseImpl(customerRepository);
    }

    private Ride buildRide(
            Customer customer,
            Driver driver,
            RideType type,
            Location startPoint,
            Location destination
    ) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl(customerUseCase);

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

    @ParameterizedTest(name = "{index}. {0} from {1} to {2}")
    @CsvSource({
            "TRIP, Paris, Outside Paris",
            "JOURNEY, Outside Paris, Paris",
            "JOURNEY, Paris, Paris",
            "TRIP, Outside Paris, Outside Paris",
    })
    @DisplayName("Book a Ride When Customer has Sufficient Funds")
    void itShould_bookRide_WithSufficientFunds(
            RideType type,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        Ride ride = buildRide(customer, driver, type, startPoint, destination);
        double initialFunds = customer.getFunds();

        // Act
        Ride bookedRide = underTest.bookRide(ride);

        // Assert
        assertNotNull(bookedRide);
        assertEquals(customer.getFunds(), initialFunds);
        assertEquals(1, rideRepository.findAll().size());


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
        Ride ride = buildRide(customer, driver, type, startPoint, destination);
        customer.setFunds(0);
        double initialFunds = customer.getFunds();

        // Act
        assertThatThrownBy(() -> underTest.bookRide(ride))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds to deduct: " + ride.getPrice());

        // Assert
        assertEquals(customer.getFunds(), initialFunds);
        assertEquals(0, rideRepository.findAll().size());
    }
}