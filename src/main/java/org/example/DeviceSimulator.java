package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class DeviceSimulator {
    private final static String QUEUE_NAME = "hori_json";
    private final static String DEVICE_ID = "1"; // Replace with actual device ID
    private final static String EXCHANGE_NAME = "hori_exchange";
    private final static String ROUTING_KEY = "hori_routing_json_key";

    private static final String DEVICE_CHANGES_TOPIC = "device.changes";

    private static void publishDeviceChange(Channel channel, String deviceId, String changeType) throws IOException {
        String message = String.format("{\"deviceId\":\"%s\",\"changeType\":\"%s\"}", deviceId, changeType);
        channel.basicPublish(EXCHANGE_NAME, DEVICE_CHANGES_TOPIC, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "' to topic: " + DEVICE_CHANGES_TOPIC);
    }

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Replace with actual RabbitMQ server address
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/example/sensor.csv"))) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            br.readLine(); // skip header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String timestamp = parts[0].trim();
                String measurementValue = parts[1].trim();

                String jsonMessage = String.format("{\"timestamp\":%s,\"device_id\":\"%s\",\"measurement_value\":%s}",
                        timestamp, DEVICE_ID, measurementValue);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, jsonMessage.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + jsonMessage + "'");

                TimeUnit.SECONDS.sleep(5); // You may want to remove or adjust this for real-time simulation
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}