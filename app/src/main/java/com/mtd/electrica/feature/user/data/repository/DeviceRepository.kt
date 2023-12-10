package com.mtd.electrica.feature.user.data.repository

import com.mtd.electrica.feature.user.data.model.EnergyConsumptionData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface DeviceRepository {
    fun getUserDevices(userId: Long): Single<List<Device>>

    fun associateDeviceWithUser(deviceId: Long, userId: Long): Single<Response<Void>>

    fun getEnergyConsumptionData(deviceId: Long, date: String): Single<List<EnergyConsumptionData>>
}