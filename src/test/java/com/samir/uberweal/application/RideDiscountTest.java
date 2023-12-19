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

import static org.junit.jupiter.api.Assertions.*;

class RideDiscountTest {
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
        RideRepository rideRepository = new RideRepositoryStub();
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
            Location destination,
            double distance
    ) {
        RideCompletionObserver completionObserver = new RideCompletionObserverImpl(customerUseCase);

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
        Ride ride = buildRide(customer, driver, type, startPoint, destination, 0);
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
        Ride ride = buildRide(customer, driver, type, startPoint, destination, distance);

        // Act
        Ride bookedRide = underTest.bookRide(ride);
        double price = bookedRide.getPrice();
        // Assert
        assertEquals(cost - 5, price);
        assertEquals(type, bookedRide.getRideType());
    }


}