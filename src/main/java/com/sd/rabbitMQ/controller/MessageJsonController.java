package com.sd.rabbitMQ.controller;

import com.sd.rabbitMQ.dto.Measurement;
import com.sd.rabbitMQ.publisher.RabbitMQJsonProducer;
import com.sd.rabbitMQ.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class MessageJsonController {

    private RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody Measurement measurement) {
        jsonProducer.sendJsonMessage(measurement);
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }
}
