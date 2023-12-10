package com.mtd.electrica.feature.user.data.dataSource.remote

import com.mtd.electrica.feature.user.data.model.EnergyConsumptionData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WebSocketAPI {
    @GET("api/v1/device/{deviceId}/consumption/{date}")
    fun getEnergyConsumptionByDeviceAndDate(
        @Path("deviceId") deviceId: Long,
        @Path("date") date: String
    ): Single<List<EnergyConsumptionData>>
}