package com.sd.rabbitMQ.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Measurement {
        private long timestamp;
        @JsonProperty("device_id")
        private String deviceID;
        @JsonProperty("measurement_value")
        private BigDecimal measurementValue;

}
