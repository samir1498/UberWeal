package com.samir.uberweal.infrastructure.config;
import com.samir.uberweal.infrastructure.gateways.BookRideDsGatewayImpl;
import com.samir.uberweal.infrastructure.gateways.ListPastRidesDsGatewayImpl;
import com.samir.uberweal.infrastructure.gateways.RiderDsGatewayImpl;
import com.samir.uberweal.application.query.handler.GetAllRidesQueryHandler;
import com.samir.uberweal.application.query.handler.GetRiderQueryHandler;
import com.samir.uberweal.adapters.gateways.BookRideDsGateway;
import com.samir.uberweal.adapters.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.adapters.gateways.RiderDsGateway;
import com.samir.uberweal.domain.services.discount.DiscountService;
import com.samir.uberweal.domain.services.pricing.RideChargeCalculatorFactory;
import com.samir.uberweal.domain.services.pricing.FinalChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.JourneyChargeCalculator;
import com.samir.uberweal.domain.services.pricing.strategies.TripChargeCalculator;
import com.samir.uberweal.infrastructure.jpa.repositories.JpaRideRepository;
import com.samir.uberweal.infrastructure.jpa.repositories.JpaRiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final JpaRideRepository rideRepository;
    private final JpaRiderRepository riderRepository;

    //todo move to a config file
    @Bean
    public GetAllRidesQueryHandler getAllRidesQueryHandler(){
        return new GetAllRidesQueryHandler(listPastRidesDsGateway());
    }

    @Bean
    public ListPastRidesDsGateway listPastRidesDsGateway(){
        return new ListPastRidesDsGatewayImpl(rideRepository);
    }

    @Bean
    public BookRideDsGateway bookRideDsGateway(){
        return new BookRideDsGatewayImpl(rideRepository);
    }

    @Bean
    public RiderDsGateway riderDsGateway(){
        return new RiderDsGatewayImpl(riderRepository);
    }

    @Bean
    public GetRiderQueryHandler getRiderQueryHandler(){
        return new GetRiderQueryHandler(riderDsGateway());
    }


    @Bean
    public RideChargeCalculatorFactory calculatorFactory(){
        return new RideChargeCalculatorFactory(new TripChargeCalculator(), new JourneyChargeCalculator());
    }
    @Bean
    public DiscountService discountService(){
        return new DiscountService();
    }
    @Bean
    public FinalChargeCalculator priceCalculator(){
        return new FinalChargeCalculator(discountService(), calculatorFactory());
    }


}
