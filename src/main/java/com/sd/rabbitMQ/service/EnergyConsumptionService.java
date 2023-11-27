package com.sd.rabbitMQ.service;

import com.sd.rabbitMQ.dto.Measurement;
import com.sd.rabbitMQ.entity.HourlyEnergyConsumption;
import com.sd.rabbitMQ.publisher.RabbitMQProducer;
import com.sd.rabbitMQ.repository.HourlyEnergyConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EnergyConsumptionService {

    @Autowired
    private HourlyEnergyConsumptionRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void processEnergyData(Measurement measurement) {
        // Convert timestamp to LocalDateTime
        LocalDateTime timestamp = Instant.ofEpochMilli(measurement.getTimestamp())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Round down to the nearest hour to aggregate on an hourly basis
        LocalDateTime hour = timestamp.truncatedTo(ChronoUnit.HOURS);

        LOGGER.info("Hour rounded down: {}", hour);

        // Check if there's already an entry for this device and hour
        Optional<HourlyEnergyConsumption> existingData = repository.findByDeviceIdAndTimestamp(measurement.getDeviceID(), hour);

        if (existingData.isPresent()) {
            // Update existing record
            HourlyEnergyConsumption consumption = existingData.get();
            consumption.setEnergyConsumed(consumption.getEnergyConsumed().add(BigDecimal.valueOf(measurement.getMeasurementValue().doubleValue())));
            repository.save(consumption);
        } else {
            // Create new record for new hour
            HourlyEnergyConsumption consumption = new HourlyEnergyConsumption();
            consumption.setDeviceId(measurement.getDeviceID());
            consumption.setTimestamp(hour);
            consumption.setEnergyConsumed(BigDecimal.valueOf(measurement.getMeasurementValue().doubleValue()));
            repository.save(consumption);
        }
    }
}
