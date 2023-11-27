package com.sd.rabbitMQ.service;

import com.sd.rabbitMQ.dto.Measurement;
import com.sd.rabbitMQ.entity.HourlyEnergyConsumption;
import com.sd.rabbitMQ.repository.HourlyEnergyConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class EnergyDataService {

    @Autowired
    private HourlyEnergyConsumptionRepository repository;

    public HourlyEnergyConsumption storeEnergyData(Measurement measurement) {
        HourlyEnergyConsumption consumption = new HourlyEnergyConsumption();
        consumption.setDeviceId(measurement.getDeviceID());
        consumption.setTimestamp(convertTimestamp(measurement.getTimestamp()));
        consumption.setEnergyConsumed(measurement.getMeasurementValue());
        return repository.save(consumption);
    }

    private LocalDateTime convertTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}

