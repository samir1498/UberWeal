package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.application.query.queries.PreAuthorizeQuery;
import com.samir.uberweal.application.query.handler.PreAuthorizeQueryHandler;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.domain.exceptions.RiderNotFoundException;
import com.samir.uberweal.adapters.gateways.RiderDsGateway;
import com.samir.uberweal.adapters.gateways.stubs.RiderDsGatewayStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RiderPreAuthorizeTest {

    private Rider rider;
    private PreAuthorizeQueryHandler underTest;

    @BeforeEach
    void setUp() {
        RiderDsGateway riderDsGateway = new RiderDsGatewayStub();
        underTest = new PreAuthorizeQueryHandler(riderDsGateway);
        rider = Rider
                .builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        riderDsGateway.save(rider);
    }

    @AfterEach
    void tearDown() {
        rider.setId(1L);
    }

    @Test
    @DisplayName("It Should preAuthorize When Rider Has Sufficient Funds")
    void itShould_preAuthorize_WhenRiderHasSufficientFunds() {
        // Arrange
        double rideCost = 50;
        PreAuthorizeQuery query = new PreAuthorizeQuery(rider, rideCost);

        // Act
        Rider preAuthRider = underTest.handle(query);
        // Assert
        assertNotNull(preAuthRider);
        assertThat(preAuthRider).isEqualTo(rider);
    }


    @Test
    @DisplayName("It Should Not preAuthorize When Rider Doesn't Exists ")
    void itShouldNot_preAuthorize_WhenRiderDoesNotExists() {
        // Arrange
        double rideCost = 50;
        rider.setId(2L);
        PreAuthorizeQuery query = new PreAuthorizeQuery(rider, rideCost);
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.handle(query))
                .isInstanceOf(RiderNotFoundException.class)
                .hasMessageContaining("Rider not found with ID: " + rider.getId());
    }

    @Test
    @DisplayName("It Should Not preAuthorize When Rider Doesn't Have Sufficient Funds")
    void itShouldNot_preAuthorize_WhenRiderDoesNotHaveSufficientFunds() {
        // Arrange
        double rideCost = 50;
        rider.setFunds(0);
        PreAuthorizeQuery query = new PreAuthorizeQuery(rider, rideCost);

        // Act
        // Assert
        assertThatThrownBy(() -> underTest.handle(query))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds to deduct: " + rideCost);
    }
}