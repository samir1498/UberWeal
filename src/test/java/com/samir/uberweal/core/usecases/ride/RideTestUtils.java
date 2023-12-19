package com.samir.uberweal.core.usecases.ride;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.entities.driver.Driver;
import com.samir.uberweal.core.domain.observers.RideCompletionObserverImpl;
import com.samir.uberweal.core.domain.repositories.customer.CustomerRepositoryStub;
import com.samir.uberweal.core.domain.repositories.ride.RideRepository;
import com.samir.uberweal.core.domain.repositories.ride.RideRepositoryStub;
import com.samir.uberweal.core.domain.services.DiscountService;
import com.samir.uberweal.core.domain.services.DiscountServiceImpl;
import com.samir.uberweal.core.usecases.customer.CustomerUseCaseImpl;

import java.time.LocalDate;

public class RideTestUtils {

    public static class SetupResult {
        public RideUseCase underTest;
        public RideCompletionObserverImpl completionObserver;
        public Customer customer;
        public Driver driver;
        public RideRepository rideRepository;

        public SetupResult(RideUseCase underTest, RideCompletionObserverImpl completionObserver,
                           Customer customer, Driver driver, RideRepository rideRepository) {
            this.underTest = underTest;
            this.completionObserver = completionObserver;
            this.customer = customer;
            this.driver = driver;
            this.rideRepository = rideRepository;
        }
    }

    public static SetupResult commonSetup() {
        CustomerRepositoryStub customerRepositoryStub = new CustomerRepositoryStub();
        CustomerUseCaseImpl customerUseCase = new CustomerUseCaseImpl(customerRepositoryStub);

        RideRepository rideRepository = new RideRepositoryStub();

        RideUseCase underTest = setupRideUseCase(rideRepository, customerUseCase);
        RideCompletionObserverImpl completionObserver = new RideCompletionObserverImpl(underTest, customerUseCase);

        Customer customer = Customer.builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        customerRepositoryStub.save(customer);

        Driver driver = Driver.builder()
                .name("driver")
                .id(1L)
                .build();

        return new SetupResult(underTest, completionObserver, customer, driver, rideRepository);
    }

    private static RideUseCase setupRideUseCase(RideRepository rideRepository, CustomerUseCaseImpl customerUseCase) {
        DiscountService discountService = new DiscountServiceImpl();

        TripRideChargeCalculator tripRideChargeCalculator = new TripRideChargeCalculator();
        JourneyRideChargeCalculator journeyRideChargeCalculator = new JourneyRideChargeCalculator();
        RideChargeCalculatorFactory rideChargeCalculatorFactory =
                new RideChargeCalculatorFactory(tripRideChargeCalculator, journeyRideChargeCalculator);

        return new RideUseCaseImpl(rideRepository, customerUseCase, discountService, rideChargeCalculatorFactory);
    }
}
