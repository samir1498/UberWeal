package com.samir.uberweal.infrastructure.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="rider")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderDataMapper {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double funds;
    private LocalDate joinedAt;
    private boolean voucher;
}
