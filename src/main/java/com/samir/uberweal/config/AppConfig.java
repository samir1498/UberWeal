package com.samir.uberweal.config;
import com.samir.uberweal.adapters.gateways.BookRideDsGatewayImpl;
import com.samir.uberweal.adapters.gateways.ListPastRidesDsGatewayImpl;
import com.samir.uberweal.adapters.gateways.RiderDsGatewayImpl;
import com.samir.uberweal.application.queryhandler.GetAllRidesQueryHandler;
import com.samir.uberweal.application.queryhandler.GetRiderQueryHandler;
import com.samir.uberweal.domain.gateways.BookRideDsGateway;
import com.samir.uberweal.domain.gateways.ListPastRidesDsGateway;
import com.samir.uberweal.domain.gateways.RiderDsGateway;
import com.samir.uberweal.infrastructure.repositories.JpaRideRepository;
import com.samir.uberweal.infrastructure.repositories.JpaRiderRepository;
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

}
