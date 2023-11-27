package com.sd.rabbitMQ.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hourlyEnergyConsumption")
public class HourlyEnergyConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal energyConsumed;
    private String deviceId;


    public void setEnergyConsumed(BigDecimal energyConsumed) {
        this.energyConsumed = energyConsumed;
    }
}
