package com.samir.uberweal.core.usecases.customer;

import com.samir.uberweal.core.domain.entities.customer.Customer;
import com.samir.uberweal.core.domain.exceptions.CustomerNotFoundException;
import com.samir.uberweal.core.domain.exceptions.InsufficientFundsException;
import com.samir.uberweal.core.domain.repositories.customer.CustomerRepository;
import com.samir.uberweal.core.domain.repositories.customer.CustomerRepositoryStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CustomerUseCaseTest {

    private Customer customer;
    private CustomerUseCase underTest;

    @BeforeEach
    void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryStub();
        underTest = new CustomerUseCaseImpl(customerRepository);
        customer = Customer
                .builder()
                .funds(100)
                .id(1L)
                .joinedAt(LocalDate.now())
                .build();
        customerRepository.save(customer);
    }

    @AfterEach
    void tearDown() {
        customer.setId(1L);
    }

    @Test
    @DisplayName("It Should preAuthorize When Customer Has Sufficient Funds")
    void itShould_preAuthorize_WhenCustomerHasSufficientFunds() {
        // Arrange
        double rideCost = 50;
        // Act
        Customer preAuthCustomer = underTest.preAuthorize(customer, rideCost);
        // Assert
        assertNotNull(preAuthCustomer);
        assertThat(preAuthCustomer).isEqualTo(customer);
    }


    @Test
    @DisplayName("It Should deduct When Customer Has Sufficient Funds")
    void itShould_deductFunds_WhenCustomerHasSufficientFunds() {
        // Arrange
        double rideCost = 50;
        double initialFunds = customer.getFunds();
        // Act
        underTest.deductFunds(customer, rideCost);
        // Assert
        assertNotNull(customer);
        assertEquals(customer.getFunds(), initialFunds - rideCost);
    }

    @Test
    @DisplayName("It Should Not preAuthorize When Customer Doesn't Exists ")
    void itShouldNot_preAuthorize_WhenCustomerDoesNotExists() {
        // Arrange
        double rideCost = 50;
        customer.setId(2L);
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.preAuthorize(customer, rideCost))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found with ID: " + customer.getId());
    }

    @Test
    @DisplayName("It Should Not preAuthorize When Customer Doesn't Have Sufficient Funds")
    void itShouldNot_preAuthorize_WhenCustomerDoesNotHaveSufficientFunds() {
        // Arrange
        double rideCost = 50;
        customer.setFunds(0);
        // Act
        // Assert
        assertThatThrownBy(() -> underTest.preAuthorize(customer, rideCost))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Insufficient funds to deduct: " + rideCost);
    }
}