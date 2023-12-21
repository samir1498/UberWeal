package com.samir.uberweal.infrastructure.JpaEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ride")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDataMapper {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private RiderDataMapper rider;
    private String startLocation;
    private String endLocation;
    private String rideType;
    private double price;
    private double distance;
    private String status;

}
