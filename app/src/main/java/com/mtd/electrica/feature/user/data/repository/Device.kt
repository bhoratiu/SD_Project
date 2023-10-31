package com.mtd.electrica.feature.user.data.repository

data class Device(
    val id: Long,
    val description: String?,
    val maximumHourlyEnergyConsumption: Long?,
    val userIds: List<Long>?
)
