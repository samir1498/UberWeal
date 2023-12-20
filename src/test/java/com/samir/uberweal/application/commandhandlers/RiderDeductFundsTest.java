package com.samir.uberweal.application.commandhandlers;

import com.samir.uberweal.application.commands.DeductFundsCommand;
import com.samir.uberweal.core.domain.entities.Rider;
import com.samir.uberweal.core.domain.repositories.RiderRepository;
import com.samir.uberweal.core.domain.repositories.stubs.RiderRepositoryStub;
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
        RiderRepository riderRepository = new RiderRepositoryStub();
        underTest = new DeductFundsCommandHandler(riderRepository);
        rider = Rider
                .builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        riderRepository.save(rider);
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