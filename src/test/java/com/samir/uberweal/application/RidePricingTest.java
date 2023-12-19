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
import com.samir.uberweal.core.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.core.domain.services.pricing.strategies.JourneyRideChargeCalculator;
import com.samir.uberweal.core.domain.services.pricing.strategies.TripRideChargeCalculator;
import com.samir.uberweal.core.usecases.BookRideUseCaseImpl;
import com.samir.uberweal.core.usecases.CustomerUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RidePricingTest {

    private CustomerUseCase customerUseCase;

    private BookRideUseCase underTest;

    private Customer customer;
    private Driver driver;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryStub();
        customerUseCase = new CustomerUseCaseImpl(customerRepository);
        underTest = setUpBookRideUseCase(customerUseCase);

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

    private BookRideUseCase setUpBookRideUseCase(CustomerUseCase customerUseCase) {
        RideRepository rideRepository = new RideRepositoryStub();
        RideChargeCalculatorFactory rideChargeCalculatorFactor = setupRideChargeCalculatorFactory();

        return new BookRideUseCaseImpl(rideRepository, customerUseCase,rideChargeCalculatorFactor);
    }

    private static RideChargeCalculatorFactory setupRideChargeCalculatorFactory() {
        TripRideChargeCalculator tripRideChargeCalculator = new TripRideChargeCalculator();
        JourneyRideChargeCalculator journeyRideChargeCalculator = new JourneyRideChargeCalculator();
        return new RideChargeCalculatorFactory(tripRideChargeCalculator, journeyRideChargeCalculator);
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


    @ParameterizedTest(name = "{index}. {0} from {2} to {3} costs {1}€")
    @CsvSource({
            "TRIP, 30, Paris, Outside Paris",
            "JOURNEY, 0, Outside Paris, Paris",
            "JOURNEY, 10, Paris, Paris",
            "TRIP, 50, Outside Paris, Outside Paris",
    })
    @DisplayName("It Should Apply Correct Price")

    void itShould_ApplyCorrectPrice(
            RideType type,
            double expectedPrice,
            Location startPoint,
            Location destination
    ) {
        // Arrange
        Ride ride = buildRide(customer, driver, type, startPoint, destination);

        // Act
        Ride bookedRide = underTest.bookRide(ride);

        // Assert
        assertEquals(expectedPrice, bookedRide.getPrice());
        assertEquals(type, bookedRide.getRideType());
    }


}
