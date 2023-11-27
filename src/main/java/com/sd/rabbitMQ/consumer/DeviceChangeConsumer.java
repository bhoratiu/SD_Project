package com.sd.rabbitMQ.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceChangeConsumer {
    private static final String EXCHANGE_NAME = "device_changes";
    private static final String ROUTING_KEY = "device.status";

    @Autowired
    private RabbitTemplate rabbitTemplate; // Use RabbitTemplate for Spring AMQP

    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON conversion

    // Method to update device status and publish the change
    public void updateDeviceStatus(String deviceId, String newStatus) {
        // Update device status logic here...

        // Publish the device change
        publishDeviceChange(deviceId, newStatus);
    }

    // Method to publish device change messages to the topic
    private void publishDeviceChange(String deviceId, String changeType) {
        try {
            // Construct the message payload as a JSON string
            String messagePayload = objectMapper.writeValueAsString(
                    new DeviceChangeMessage(deviceId, changeType)
            );

            // Publish the message to the exchange with the routing key
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, messagePayload);

            System.out.println("Published device change -> " + messagePayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    // A helper class to structure the device change message
    private static class DeviceChangeMessage {
        private String deviceId;
        private String changeType;

        public DeviceChangeMessage(String deviceId, String changeType) {
            this.deviceId = deviceId;
            this.changeType = changeType;
        }

        // Getters and setters
        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getChangeType() {
            return changeType;
        }

        public void setChangeType(String changeType) {
            this.changeType = changeType;
        }
    }
}