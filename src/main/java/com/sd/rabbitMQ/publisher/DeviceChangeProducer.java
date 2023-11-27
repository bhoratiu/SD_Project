package com.sd.rabbitMQ.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceChangeProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange deviceChangeExchange;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void publishDeviceChange(String deviceId, String changeType) {
        String routingKey = "device.change." + changeType;
        rabbitTemplate.convertAndSend(deviceChangeExchange.getName(), routingKey, deviceId);
        LOGGER.info(String.format("Published device change -> %s: %s", changeType, deviceId));
    }
}

