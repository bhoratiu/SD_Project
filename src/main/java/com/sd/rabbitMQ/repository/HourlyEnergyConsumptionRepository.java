package com.sd.rabbitMQ.repository;

import com.sd.rabbitMQ.entity.HourlyEnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface HourlyEnergyConsumptionRepository extends JpaRepository<HourlyEnergyConsumption, Long> {
    Optional<HourlyEnergyConsumption> findByDeviceIdAndTimestamp(String deviceId, LocalDateTime hour);
}
