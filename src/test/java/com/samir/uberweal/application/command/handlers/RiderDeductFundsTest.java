package com.samir.uberweal.application.command.handlers;

import com.samir.uberweal.application.command.commands.DeductFundsCommand;
import com.samir.uberweal.domain.entities.Rider;
import com.samir.uberweal.domain.gateways.RiderDsGateway;
import com.samir.uberweal.domain.gateways.stubs.RiderDsGatewayStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RiderDeductFundsTest {

    private Rider rider;
    private DeductFundsCommandHandler underTest;

    @BeforeEach
    void setUp() {
        RiderDsGateway riderDsGateway = new RiderDsGatewayStub();
        underTest = new DeductFundsCommandHandler(riderDsGateway);
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
    @DisplayName("It Should deduct When Rider Has Sufficient Funds")
    void itShould_deductFunds_WhenRiderHasSufficientFunds() {
        // Arrange
        double rideCost = 50;
        double initialFunds = rider.getFunds();
        DeductFundsCommand deductFundsCommand = new DeductFundsCommand(rider.getId(), rideCost);
        // Act
        underTest.handle(deductFundsCommand);
        // Assert
        assertNotNull(rider);
        assertEquals(rider.getFunds(), initialFunds - rideCost);
    }

}