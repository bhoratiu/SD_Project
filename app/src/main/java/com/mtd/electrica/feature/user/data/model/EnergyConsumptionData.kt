package com.mtd.electrica.feature.user.data.model

import java.time.LocalDateTime

data class EnergyConsumptionData(
    val id: Long,
    val timestamp: String,
    val energyConsumed: Double,
    val deviceId: String
)
