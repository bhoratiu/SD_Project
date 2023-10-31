package com.electrica.Electrica.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double maximumHourlyEnergyConsumption;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "device_user_associations", joinColumns = @JoinColumn(name = "device_id"))
    @Column(name = "user_id")
    private List<Long> userIds = new ArrayList<>();
}
