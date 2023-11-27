package com.sd.rabbitMQ.consumer;

import com.sd.rabbitMQ.dto.Measurement;
import com.sd.rabbitMQ.entity.HourlyEnergyConsumption;
import com.sd.rabbitMQ.repository.HourlyEnergyConsumptionRepository;
import com.sd.rabbitMQ.service.EnergyConsumptionService;
import com.sd.rabbitMQ.service.EnergyDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class RabbitMQJsonConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    private final EnergyConsumptionService energyConsumptionService;

    @Autowired
    public RabbitMQJsonConsumer(EnergyConsumptionService energyConsumptionService) {
        this.energyConsumptionService = energyConsumptionService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(Measurement measurement) {
        LOGGER.info(String.format("Received JSON message -> %s", measurement.toString()));
        energyConsumptionService.processEnergyData(measurement);
    }
}

